package de.district.api.event.economy;

import de.district.api.entity.PluginPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

abstract class BalanceEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final PluginPlayer player;

    protected BalanceEvent(@NotNull final PluginPlayer player) {
        this.player = player;
    }

    protected BalanceEvent(@NotNull final PluginPlayer player, final boolean async) {
        super(async);
        this.player = player;
    }

    @NotNull
    public PluginPlayer getPlayer() {
        return player;
    }

    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
