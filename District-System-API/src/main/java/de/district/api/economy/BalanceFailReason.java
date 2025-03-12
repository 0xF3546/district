package de.district.api.economy;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code BalanceFailReason} enum represents various reasons why a balance-related operation
 * within the economy system might fail.
 * Each enum constant corresponds to a specific failure reason,
 * such as insufficient funds or an invalid amount.
 *
 * <p>This enum also provides methods to retrieve the associated failure reason as a string
 * and to find a {@code BalanceFailReason} by its translation key.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public enum BalanceFailReason {
    USER_NOT_FOUND("UserNotFound"),
    INSUFFICIENT_FUNDS("InsufficientFunds"),
    INVALID_AMOUNT("InvalidAmount"),
    TRANSFER_EXCEEDS_MAX_VALUE("TransferExceedsMaxValue"),
    UNKNOWN("Unknown");

    private final String reason;

    /**
     * Constructs a {@code BalanceFailReason} with the specified reason message.
     *
     * @param message the reason message associated with the failure reason, must not be {@code null}.
     */
    BalanceFailReason(@NotNull final String message) {
        this.reason = message;
    }

    @Contract("null -> null")
    @Nullable
    public static BalanceFailReason fromTranslationKey(@Nullable final String message) {
        if (message == null) {
            return null;
        }

        for (BalanceFailReason reason : values()) {
            if (reason.getReason().equals(message)) {
                return reason;
            }
        }
        return null;
    }

    /**
     * Retrieves the translation key associated with this {@code BalanceFailReason}.
     *
     * @return the translation key, never {@code null}.
     */
    @NotNull
    public String getReason() {
        return reason;
    }
}
