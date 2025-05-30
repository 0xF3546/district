package de.district.api.entity;

import de.district.api.command.PluginCommandSender;
import de.district.api.location.LocationSearchable;
import de.district.api.util.Prefix;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;

import java.util.UUID;

/**
 * The {@code PluginPlayer} interface extends both {@link PluginOfflinePlayer} and {@link LocationSearchable}
 * to represent a player who is currently online in the Bukkit server environment. This interface provides
 * additional methods to interact with the online player, including sending messages with or without a prefix.
 *
 * <p>Implementations of this interface are expected to manage online player data and facilitate interactions
 * that require the player to be online, such as sending chat messages or searching locations.</p>
 *
 * @author Erik Pförtner
 * @see PluginOfflinePlayer
 * @see LocationSearchable
 * @see Player
 * @since 1.0.0
 */
public interface PluginPlayer extends PluginOfflinePlayer, LocationSearchable, PluginCommandSender {

    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player, never {@code null}.
     */
    @NotNull
    String getName();

    @NotNull
    UUID getUUID();

    /**
     * Retrieves the {@link Player} instance associated with this {@code PluginPlayer}.
     * This allows access to the player's data and status within the Bukkit server environment while they are online.
     *
     * @return the {@link Player} associated with this plugin-specific player, never {@code null}.
     */
    @NotNull
    Player getBukkitPlayer();

    /**
     * Sends a message to the player.
     *
     * @param message the message to send, never {@code null}.
     * @deprecated Use {@link #sendMessage(Component)} or {@link #sendMessage(ComponentLike)} instead.
     */
    @Deprecated
    void sendMessage(@NotNull final String message);

    /**
     * Sends a message to the player, with an optional prefix.
     * This method is implemented in the core implementation and should be overridden for custom implementations.
     * (or not because it's deprecated)
     *
     * @param message the message to send, never {@code null}.
     * @param prefix  whether to include a prefix in the message.
     * @throws UnsupportedOperationException - Implemented in the core implementation. For custom implementations, this method should be overridden.
     * @deprecated This method is not implemented and will throw an exception.
     */
    @Deprecated
    default void sendMessage(@NotNull final String message, final boolean prefix) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Sends a message to the player as a {@link Component}, with a default prefix.
     *
     * @param message the message to send, never {@code null}.
     */
    default void sendMessage(@NotNull final Component message) {
        sendMessage(message, true);
    }

    /**
     * Sends a message to the player as a {@link Component}, with an optional prefix.
     * This method is implemented in the core implementation and should be overridden for custom implementations.
     *
     * @param message the message to send, never {@code null}.
     * @param prefix  whether to include a prefix in the message.
     * @throws UnsupportedOperationException - Implemented in the core implementation. For custom implementations, this method should be overridden.
     */
    default void sendMessage(@NotNull final Component message, final Prefix prefix) {
        this.sendMessage(Component.text(prefix.getPrefix() + message));
    }

    /**
     * Sends a message to the player as a {@link ComponentLike}, converting it to a {@link Component}.
     *
     * @param message the message to send, never {@code null}.
     */
    default void sendMessage(@NotNull final ComponentLike message) {
        this.sendMessage(message.asComponent());
    }

    /**
     * Retrieves whether the player is currently on duty.
     *
     * @return {@code true} if the player is on duty, {@code false} otherwise.
     */
    boolean isAduty();

    /**
     * Sets whether the player is on duty.
     *
     * @param state {@code true} if the player is on duty, {@code false} otherwise.
     */
    void setAduty(final boolean state);

    /**
     * Retrieves the player's current character.
     *
     * @return the player's current character, or {@code null} if the player does not have a character.
     */
    @Nullable
    PlayerCharacter getCharacter();

    /**
     * Creates a new character associated with the player. This character contains the player's
     * personal details, such as first name, last name, gender, and date of birth.
     *
     * @param firstName   the first name of the character, never {@code null}.
     * @param lastName    the last name of the character, never {@code null}.
     * @param gender      the gender of the character, represented as a single character (e.g., 'M', 'F').
     * @param dateOfBirth the birth date of the character, never {@code null}.
     */
    void createCharacter(@NotNull final String firstName,
                         @NotNull final String lastName,
                         final char gender,
                         @NotNull final LocalDateTime dateOfBirth);
}
