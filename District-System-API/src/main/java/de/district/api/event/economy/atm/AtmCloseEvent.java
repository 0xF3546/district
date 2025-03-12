package de.district.api.event.economy.atm;

import de.district.api.entity.PluginPlayer;
import org.jetbrains.annotations.NotNull;

public class AtmCloseEvent extends AtmEvent {
    public AtmCloseEvent(@NotNull final PluginPlayer player) {
        super(player);
    }
}
