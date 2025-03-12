package de.district.api.event.economy;

import de.district.api.entity.PluginPlayer;
import org.jetbrains.annotations.NotNull;

public class BalanceChangeEvent extends BalanceEvent {
    private final Action action;
    private final double amount;
    private final double oldBalance;

    protected BalanceChangeEvent(final @NotNull PluginPlayer player, final @NotNull Action action, final double amount, final double oldBalance) {
        super(player);
        this.action = action;
        this.amount = amount;
        this.oldBalance = oldBalance;
    }

    public @NotNull Action getAction() {
        return action;
    }

    public double getAmount() {
        return amount;
    }

    public double getOldBalance() {
        return oldBalance;
    }

    public enum Action {
        ADD,
        REMOVE,
        SET
    }
}
