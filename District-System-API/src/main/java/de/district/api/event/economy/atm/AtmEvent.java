package de.district.api.event.economy.atm;

import de.district.api.entity.PluginPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

abstract class AtmEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final PluginPlayer player;

    protected AtmEvent(@NotNull final PluginPlayer player) {
        this.player = player;
    }

    protected AtmEvent(@NotNull final PluginPlayer player, final boolean async) {
        super(async);
        this.player = player;
    }

    @NotNull
    public PluginPlayer getPlayer() {
        return player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
