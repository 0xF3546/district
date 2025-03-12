package de.district.api.event.economy.atm;

import de.district.api.entity.PluginPlayer;
import org.jetbrains.annotations.NotNull;

public class AtmOpenEvent extends AtmEvent {
    public AtmOpenEvent(@NotNull final PluginPlayer player) {
        super(player);
    }
}
