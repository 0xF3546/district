package de.district.core.economy;

import de.district.api.DistrictRoleplayAPI;
import de.district.api.economy.BalanceAccessor;
import de.district.api.economy.BalanceFailReason;
import de.district.core.economy.service.EconomyService;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code CoreBalanceAccessor} class implements the {@link BalanceAccessor} interface to provide
 * access and manipulation of a player's balance within the District Roleplay economy system. This class
 * interacts with the {@link EconomyService} to perform balance-related operations, such as getting, setting,
 * adding, removing, and transferring balances.
 *
 * <p>This class is instantiated with an {@link OfflinePlayer} and uses the {@link DistrictRoleplayAPI} to retrieve
 * the necessary {@link EconomyService} bean for executing the operations.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public class CoreBalanceAccessor implements BalanceAccessor {
    private final OfflinePlayer player;

    /**
     * Constructs a new {@code CoreBalanceAccessor} for the specified {@link OfflinePlayer}.
     *
     * @param player the player whose balance is to be accessed and manipulated.
     */
    public CoreBalanceAccessor(@NotNull final OfflinePlayer player) {
        this.player = player;
    }

    /**
     * Retrieves the current balance of the player.
     *
     * @return the player's current balance.
     */
    @Override
    public double get() {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.getBalance(player.getUniqueId());
    }

    /**
     * Sets the player's balance to the specified amount.
     *
     * @param balance the balance to set.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    @Override
    public Optional<BalanceFailReason> set(final double balance) {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.setBalance(player.getUniqueId(), balance);
    }

    /**
     * Adds the specified amount to the player's balance.
     *
     * @param balance the amount to add.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    @Override
    public Optional<BalanceFailReason> add(final double balance) {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.addBalance(player.getUniqueId(), balance);
    }

    /**
     * Removes the specified amount from the player's balance.
     *
     * @param balance the amount to remove.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    @Override
    public Optional<BalanceFailReason> remove(final double balance) {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.removeBalance(player.getUniqueId(), balance);
    }

    /**
     * Transfers the specified amount from the player's balance to another player's balance.
     *
     * @param balance the amount to transfer.
     * @param target  the UUID of the player to transfer the balance to.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    @Override
    public Optional<BalanceFailReason> transfer(final double balance, final @NotNull UUID target) {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.transferBalance(player.getUniqueId(), target, balance);
    }

    /**
     * Resets the player's balance to zero.
     *
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    @Override
    public Optional<BalanceFailReason> reset() {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.resetBalance(player.getUniqueId());
    }

    /**
     * Checks if the player's balance is greater than or equal to the specified amount.
     *
     * @param balance the amount to check.
     * @return {@code true} if the player has sufficient balance, {@code false} otherwise.
     */
    @Override
    public boolean has(final double balance) {
        EconomyService economyService = DistrictRoleplayAPI.getBean(EconomyService.class);
        return economyService.hasBalance(player.getUniqueId(), balance).isEmpty();
    }
}
