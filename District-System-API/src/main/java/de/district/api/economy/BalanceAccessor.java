package de.district.api.economy;

import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.UUID;

/**
 * The {@code BalanceAccessor} interface provides methods for managing and manipulating a user's balance within
 * the economy system.
 * This includes operations such as setting, adding, removing, and transferring balances,
 * as well as checking and resetting the balance.
 *
 * <p>Implementations of this interface are expected to handle all balance-related operations and ensure that
 * any failures are captured and returned as {@link BalanceFailReason}.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public interface BalanceAccessor {

    /**
     * Retrieves the current balance.
     *
     * @return the current balance as a double.
     */
    double get();

    /**
     * Sets the balance to a specified amount.
     *
     * @param balance the new balance to set.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    Optional<BalanceFailReason> set(final double balance);

    /**
     * Adds a specified amount to the current balance.
     *
     * @param balance the amount to add.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    Optional<BalanceFailReason> add(final double balance);

    /**
     * Removes a specified amount from the current balance.
     *
     * @param balance the amount to remove.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    Optional<BalanceFailReason> remove(final double balance);

    /**
     * Transfers a specified amount from the current balance to another user's balance.
     *
     * @param balance the amount to transfer.
     * @param target  the UUID of the target user to whom the balance is transferred.
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    Optional<BalanceFailReason> transfer(final double balance, @NotNull final UUID target);

    /**
     * Resets the balance to its default state.
     *
     * @return an {@link Optional} containing a {@link BalanceFailReason} if the operation fails, or empty if successful.
     */
    Optional<BalanceFailReason> reset();

    /**
     * Checks if the current balance is at least the specified amount.
     *
     * @param balance the amount to check.
     * @return {@code true} if the current balance is greater than or equal to the specified amount,
     * {@code false} otherwise.
     */
    boolean has(final double balance);

    /**
     * Retrieves the bank provider associated with the player.
     *
     * @return the bank provider associated with the player.
     */
    @NotNull
    String getBankProvider();
}
