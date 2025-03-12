package de.district.api.economy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code Accountable} interface extends {@link Chargeable} and provides additional methods for managing
 * bank accounts and balances within the economy system. Implementations of this interface are expected to handle
 * the creation of bank accounts, managing providers, and accessing balances.
 *
 * @see Chargeable
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface Accountable extends Chargeable {

    /**
     * Opens a new bank account with the specified provider and type, optionally considering if there is an old account.
     *
     * @param provider the bank provider's name, must not be {@code null}.
     * @param type the type of the bank account, typically represented by an enum.
     * @param hasOldAccount a boolean indicating whether the user already has an account with the provider.
     */
    void openNewBankAccount(@NotNull final String provider, final BankType type, final boolean hasOldAccount);

    /**
     * Checks if the user has a bank provider associated with their account.
     *
     * @return {@code true} if the user has a bank provider, {@code false} otherwise.
     */
    boolean hasBankProvider();

    /**
     * Retrieves the name of the bank provider associated with the user's account.
     *
     * @return the name of the bank provider, or {@code null} if no provider is associated.
     */
    @Nullable
    String getBankProvider();

    /**
     * Sets the bank provider for the user's account.
     *
     * @param provider the name of the bank provider, must not be {@code null}.
     */
    void setBankProvider(@NotNull final String provider);

    /**
     * Retrieves the {@link BalanceAccessor} associated with the user's account, which provides access to the balance.
     *
     * @return the {@link BalanceAccessor} for the user's account, or {@code null} if not available.
     */
    @Nullable
    BalanceAccessor balanceAccessor();
}
