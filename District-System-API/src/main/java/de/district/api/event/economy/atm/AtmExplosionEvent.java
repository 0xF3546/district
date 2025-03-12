package de.district.api.event.economy.atm;

import de.district.api.entity.PluginPlayer;
import org.jetbrains.annotations.NotNull;

public class AtmExplosionEvent extends AtmEvent {
    public AtmExplosionEvent(@NotNull final PluginPlayer player) {
        super(player);
    }
}
