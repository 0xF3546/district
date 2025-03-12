package de.district.api.entity;

import de.district.api.economy.Accountable;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code PluginOfflinePlayer} interface extends the {@link Accountable} interface and represents a player
 * who is not currently online in the Bukkit server environment but still retains certain data and functionalities.
 *
 * <p>This interface provides a method to retrieve the associated {@link OfflinePlayer} instance from the Bukkit API,
 * allowing for interactions with player data even when the player is offline.</p>
 *
 * <p>Implementations of this interface are expected to manage offline player data in a way that integrates
 * with the economy system, as indicated by the extension of {@link Accountable}.</p>
 *
 * @see Accountable
 * @see OfflinePlayer
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface PluginOfflinePlayer extends Accountable {

    /**
     * Retrieves the {@link OfflinePlayer} instance associated with this {@code PluginOfflinePlayer}.
     * This allows access to the player's data and status within the Bukkit server environment, even when the player is offline.
     *
     * @return the {@link OfflinePlayer} associated with this plugin-specific offline player, never {@code null}.
     */
    @NotNull
    OfflinePlayer getBukkitOfflinePlayer();
}
