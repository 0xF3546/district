package de.district.core.economy.service;

import de.district.api.economy.BalanceFailReason;
import de.district.api.economy.BankType;
import de.district.api.economy.bank.Bic;
import de.district.api.economy.bank.Iban;
import de.district.core.economy.repository.BankRepository;
import de.district.core.economy.repository.EconomyRepository;
import de.district.core.economy.domain.Bank;
import de.district.core.economy.domain.Economy;
import de.district.core.economy.domain.dto.BankDto;
import de.district.core.economy.domain.dto.EconomyDto;
import de.district.core.user.repository.UserRepository;
import de.district.core.user.domain.User;
import de.district.core.user.domain.dto.UserDto;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code EconomyService} class provides business logic for managing the economy system, including operations
 * related to user balances, bank accounts, and balance transfers. It interacts with various DAOs to persist and
 * retrieve data from the database.
 *
 * <p>This service handles operations such as setting, adding, removing, and transferring balances, as well as
 * creating and managing bank accounts for users.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Service
public class EconomyService {

    @Autowired
    private EconomyRepository economyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BankRepository bankRepository;

    /**
     * Rounds a double value down to the nearest whole number.
     *
     * @param value the value to be rounded down.
     * @return the rounded-down value.
     */
    public static double roundToLower(double value) {
        return Math.floor(value);
    }

    /**
     * Sets the balance for a specific user identified by their UUID.
     *
     * @param uuid the UUID of the user.
     * @param balance the balance to set.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    public Optional<BalanceFailReason> setBalance(@NotNull final UUID uuid, double balance) {
        // TODO: Add roundingMode in config
/*        if (roundingMode) {
            balance = roundToLower(balance);
        }*/

        try {
            User user = userRepository.findByUuid(uuid.toString()).orElseGet(() -> {
                UserDto userDto = new UserDto(uuid.toString(), System.currentTimeMillis(), "Default", false);
                User newUser = new User(userDto);
                return userRepository.save(newUser);
            });

            economyRepository.findByUser(user).ifPresentOrElse(userBalance -> {
                userBalance.setBalance(balance);
                economyRepository.save(userBalance);
            }, () -> {
                EconomyDto userBalanceDto = new EconomyDto(user, balance);
                Economy newUserBalance = new Economy(userBalanceDto);
                economyRepository.save(newUserBalance);
            });
        } catch (Exception e) {
            return Optional.of(BalanceFailReason.UNKNOWN);
        }

        return Optional.empty();
    }

    /**
     * Retrieves the balance for a specific user identified by their UUID.
     *
     * @param uuid the UUID of the user.
     * @return the user's balance.
     */
    public double getBalance(@NotNull final UUID uuid) {
        return userRepository.findByUuid(uuid.toString())
                .map(user -> economyRepository.findByUser(user).map(Economy::getBalance).orElse(0.0))
                .orElse(0.0);
    }

    /**
     * Adds a specified amount to a user's balance.
     *
     * @param uuid the UUID of the user.
     * @param amount the amount to add to the balance.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    public Optional<BalanceFailReason> addBalance(@NotNull final UUID uuid, final double amount) {
        return setBalance(uuid, getBalance(uuid) + amount);
    }

    /**
     * Removes a specified amount from a user's balance.
     *
     * @param uuid the UUID of the user.
     * @param amount the amount to remove from the balance.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    public Optional<BalanceFailReason> removeBalance(@NotNull final UUID uuid, final double amount) {
        return setBalance(uuid, getBalance(uuid) - amount);
    }

    /**
     * Checks if a user has a balance greater than or equal to a specified amount.
     *
     * @param uuid the UUID of the user.
     * @param amount the amount to check against the user's balance.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the user has insufficient funds, or empty if sufficient.
     */
    public Optional<BalanceFailReason> hasBalance(@NotNull final UUID uuid, final double amount) {
        return getBalance(uuid) >= amount ? Optional.empty() : Optional.of(BalanceFailReason.INSUFFICIENT_FUNDS);
    }

    /**
     * Transfers a specified amount from one user's balance to another.
     *
     * @param from the UUID of the user to transfer from.
     * @param to the UUID of the user to transfer to.
     * @param amount the amount to transfer.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    public Optional<BalanceFailReason> transferBalance(@NotNull final UUID from, @NotNull final UUID to, final double amount) {
        if (amount <= 0) {
            return Optional.of(BalanceFailReason.INVALID_AMOUNT);
        }

        if (hasBalance(from, amount).isEmpty()) {
            Optional<BalanceFailReason> removeBalanceFailReason = removeBalance(from, amount);
            if (removeBalanceFailReason.isPresent()) {
                return removeBalanceFailReason;
            }

            if (Double.MAX_VALUE - getBalance(to) < amount) {
                return Optional.of(BalanceFailReason.INVALID_AMOUNT);
            }

            Optional<BalanceFailReason> addBalanceFailReason = addBalance(to, amount);
            if (addBalanceFailReason.isPresent()) {
                return addBalanceFailReason;
            }
        } else {
            return Optional.of(BalanceFailReason.INSUFFICIENT_FUNDS);
        }

        return Optional.empty();
    }

    /**
     * Resets a user's balance to zero.
     *
     * @param uuid the UUID of the user.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    public Optional<BalanceFailReason> resetBalance(@NotNull final UUID uuid) {
        return setBalance(uuid, 0.0);
    }

    /**
     * Resets the balances of all users in the system.
     */
    public void resetAllBalances() {
        userRepository.findAll().forEach(user -> economyRepository.findByUser(user).ifPresent(economyRepository::delete));
    }

    /**
     * Checks if a user has a bank account.
     *
     * @param uuid the UUID of the user.
     * @return {@code true} if the user has a bank account, {@code false} otherwise.
     */
    public boolean hasBankAccount(@NotNull final UUID uuid) {
        return bankRepository.findByUser(userRepository.findByUuid(uuid.toString()).orElse(null)).isPresent();
    }

    /**
     * Creates a bank account for a user if they do not already have one.
     *
     * @param uuid the UUID of the user.
     */
    public void createBankAccount(@NotNull final UUID uuid) {
        User user = userRepository.findByUuid(uuid.toString()).orElseGet(() -> {
            UserDto userDto = new UserDto(uuid.toString(), System.currentTimeMillis(), "Default", false);
            User newUser = new User(userDto);
            return userRepository.save(newUser);
        });

        Optional<Bank> bank = bankRepository.findByUser(user);
        if (bank.isPresent()) {
            return;
        }

        BankDto bankDto = new BankDto(user, "District City Bank", BankType.BASIC, Iban.createRandomDistrictIban(uuid), new Bic("DSTCDEFFXXX"));
        Bank newBank = new Bank(bankDto);
        bankRepository.save(newBank);
    }
}
