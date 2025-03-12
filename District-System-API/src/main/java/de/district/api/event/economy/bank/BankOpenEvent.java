package de.district.api.event.economy.bank;

import de.district.api.entity.PluginPlayer;
import org.jetbrains.annotations.NotNull;

public class BankOpenEvent extends BankEvent {
    public BankOpenEvent(@NotNull final PluginPlayer player) {
        super(player);
    }
}
