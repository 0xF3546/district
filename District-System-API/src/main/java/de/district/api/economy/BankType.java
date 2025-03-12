package de.district.api.economy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code BankType} enum represents different types of bank accounts available within the economy system.
 * Each enum constant includes a display name and an account opening fee, which can be retrieved through
 * the provided methods.
 *
 * <p>This enum also provides several utility methods to retrieve a {@code BankType} by its name, display name,
 * or opening fee.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public enum BankType {
    BASIC("§8» §7Basic Account", 0.0);

    private final String displayName;
    private final double accountOpeningFee;

    /**
     * Constructs a {@code BankType} with the specified display name and account opening fee.
     *
     * @param name the display name of the bank type.
     * @param accountOpeningFee the fee required to open this type of bank account.
     */
    BankType(final String name, final double accountOpeningFee) {
        this.displayName = name;
        this.accountOpeningFee = accountOpeningFee;
    }

    /**
     * Retrieves the display name associated with this {@code BankType}.
     *
     * @return the display name of the bank type.
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Retrieves the account opening fee associated with this {@code BankType}.
     *
     * @return the account opening fee for the bank type.
     */
    public double getAccountOpeningFee() {
        return this.accountOpeningFee;
    }

    /**
     * Retrieves a {@code BankType} by its name.
     *
     * @param name the name of the bank type to retrieve.
     * @return the corresponding {@code BankType}, or {@code null} if no match is found.
     */
    @Nullable
    @Contract("null -> null")
    public static BankType getByName(@Nullable final String name) {
        if (name == null || name.isEmpty()) {
            return null;
        }

        for (final BankType bankType : values()) {
            if (bankType.name().equalsIgnoreCase(name)) {
                return bankType;
            }
        }
        return null;
    }

    /**
     * Retrieves a {@code BankType} by its display name.
     *
     * @param displayName the display name of the bank type to retrieve.
     * @return the corresponding {@code BankType}, or {@code null} if no match is found.
     */
    @Nullable
    @Contract("null -> null; _ -> null")
    public static BankType getByDisplayName(@Nullable final String displayName) {
        if (displayName == null || displayName.isEmpty()) {
            return null;
        }

        for (final BankType bankType : values()) {
            if (bankType.getDisplayName().equalsIgnoreCase(displayName)) {
                return bankType;
            }
        }
        return null;
    }

    /**
     * Retrieves a {@code BankType} by its account opening fee.
     *
     * @param accountOpeningFee the account opening fee of the bank type to retrieve.
     * @return the corresponding {@code BankType}, or {@code null} if no match is found.
     */
    @Nullable
    public static BankType getByOpeningFee(final double accountOpeningFee) {
        for (final BankType bankType : values()) {
            if (bankType.getAccountOpeningFee() == accountOpeningFee) {
                return bankType;
            }
        }
        return null;
    }
}
