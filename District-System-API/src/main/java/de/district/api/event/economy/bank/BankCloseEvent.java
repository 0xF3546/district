package de.district.api.event.economy.bank;

import de.district.api.entity.PluginPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BankCloseEvent extends BankEvent {
    public BankCloseEvent(@NotNull final PluginPlayer player) {
        super(player);
    }
}
