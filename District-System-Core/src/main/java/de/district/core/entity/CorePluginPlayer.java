package de.district.core.entity;

import de.district.api.DistrictAPI;
import de.district.api.economy.GameAtm;
import de.district.api.economy.GameBank;
import de.district.api.entity.PlayerCharacter;
import de.district.api.entity.PluginPlayer;
import de.district.core.DistrictRoleplay;
import de.district.core.character.service.CharacterService;
import de.district.core.character.util.Gender;
import de.district.core.location.service.LocationFindingService;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The {@code CorePluginPlayer} class extends {@link CorePluginOfflinePlayer}
 * and implements the {@link PluginPlayer} interface,
 * providing additional functionality for online players in the District Roleplay System (DRS).
 * This class manages player-specific
 * operations such as sending messages, finding nearby banks or ATMs, and more.
 *
 * <p>This class interacts with the {@link DistrictAPI} to handle deprecated methods
 * and to manage player interactions within the game.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public class CorePluginPlayer extends CorePluginOfflinePlayer implements PluginPlayer {
    private final Player player;
    private boolean aduty = false;

    /**
     * Constructs a new {@code CorePluginPlayer} instance for the specified {@link Player}.
     *
     * @param player the Bukkit {@code Player} associated with this instance.
     */
    public CorePluginPlayer(@NotNull final Player player) {
        super(player);
        this.player = player;
    }

    /**
     * Retrieves the name of the player.
     *
     * @return the name of the player.
     */
    @Override
    public @NotNull String getName() {
        return this.player.getName();
    }

    @Override
    public @NotNull UUID getUUID() {
        return this.player.getUniqueId();
    }

    /**
     * Retrieves the {@link Player} associated with this instance.
     *
     * @return the associated {@code Player}.
     */
    @Override
    public @NotNull Player getBukkitPlayer() {
        return this.player;
    }

    /**
     * Sends a message to the player using a deprecated method.
     * Logs a warning if the deprecated method is used and the corresponding flag is not set.
     *
     * @param message the message to send.
     */
    @Override
    public void sendMessage(final @NotNull String message) {
        String callerClassName = Thread.currentThread().getStackTrace()[1].getClassName();
        DistrictAPI.getLogger().warning(String.format("Deprecated method used: PluginPlayer#sendMessage(String) in %s", callerClassName));
        DistrictAPI.getLogger().warning("Use PluginPlayer#sendMessage(Component) instead.");
        this.player.sendMessage(message);
    }

    /**
     * Sends a message to the player with an optional prefix using a deprecated method.
     * Logs a warning if the deprecated method is used and the corresponding flag is not set.
     *
     * @param message the message to send.
     * @param prefix  whether to prepend a prefix to the message.
     */
    @Override
    public void sendMessage(final @NotNull String message, final boolean prefix) {
        String callerClassName = Thread.currentThread().getStackTrace()[1].getClassName();
        DistrictAPI.getLogger().warning(String.format("Deprecated method used: PluginPlayer#sendMessage(String, boolean) in %s", callerClassName));
        DistrictAPI.getLogger().warning("Use PluginPlayer#sendMessage(Component, boolean) instead.");
        if (prefix) {
            this.player.sendMessage(DistrictRoleplay.PREFIX + message);
        } else {
            this.player.sendMessage(message);
        }
    }

    /**
     * Sends a message to the player, with an optional prefix, using the {@link Component} API.
     *
     * @param message the message to send as a {@link Component}.
     * @param prefix  whether to prepend a prefix to the message.
     */
    @Override
    public void sendMessage(final @NotNull Component message, final boolean prefix) {
        if (prefix) {
            this.player.sendMessage(Component.text(DistrictRoleplay.PREFIX).append(message));
        } else {
            this.player.sendMessage(message);
        }
    }

    /**
     * Retrieves the player's character entity.
     *
     * @return the player's character entity, or {@code null} if the character is not found.
     */
    @Override
    public @Nullable PlayerCharacter getCharacter() {
        final CharacterService characterService = DistrictAPI.getBean(CharacterService.class);
        return characterService.findCharacterByPlayer(this);
    }

    /**
     * Creates a new character associated with the player. This character contains the player's
     * personal details, such as first name, last name, gender, and date of birth.
     *
     * @param firstName   the first name of the character, never {@code null}.
     * @param lastName    the last name of the character, never {@code null}.
     * @param genderCode  the gender of the character, represented as a single character (e.g., 'M', 'F').
     * @param dateOfBirth the birth date of the character, never {@code null}.
     */
    @Override
    public void createCharacter(@NotNull final String firstName,
                                @NotNull final String lastName,
                                final char genderCode,
                                @NotNull final LocalDateTime dateOfBirth) {
        final CharacterService characterService = DistrictAPI.getBean(CharacterService.class);
        final Gender gender = Gender.fromChar(genderCode);

        if (gender == null) {
            throw new IllegalArgumentException("Char Code should be 'M' or 'F'");
        }

        characterService.createCharacter(
                this,
                firstName,
                lastName,
                gender,
                dateOfBirth
        );
    }

    /**
     * Retrieves the player's admin duty status.
     *
     * @return {@code true} if the player is in admin duty mode, {@code false} otherwise.
     */
    @Override
    public boolean isAduty() {
        return this.aduty;
    }

    /**
     * Sets the player's admin duty status.
     *
     * @param state the new admin duty status.
     */
    @Override
    public void setAduty(final boolean state) {
        this.aduty = state;
    }

    /**
     * Finds all nearby banks within the specified radius. This method is currently unimplemented.
     *
     * @param radius the radius within which to search for nearby banks.
     * @return an empty list as this method is not yet implemented.
     */
    @Override
    public List<GameBank> findNearbyBanks(final double radius) {
        LocationFindingService lfs = DistrictAPI.getBean(LocationFindingService.class);
        return List.of();
    }

    /**
     * Finds the nearest bank to the player's location. This method is currently unimplemented.
     *
     * @return an {@link Optional} containing no value as this method is not yet implemented.
     */
    @Override
    public Optional<GameBank> findNearestBank() {
        return Optional.empty();
    }

    /**
     * Finds all nearby ATMs within the specified radius. This method is currently unimplemented.
     *
     * @param radius the radius within which to search for nearby ATMs.
     * @return an empty list as this method is not yet implemented.
     */
    @Override
    public List<GameAtm> findNearbyAtms(final double radius) {
        return List.of();
    }

    /**
     * Finds the nearest ATM to the player's location. This method is currently unimplemented.
     *
     * @return an {@link Optional} containing no value as this method is not yet implemented.
     */
    @Override
    public Optional<GameAtm> findNearestAtm() {
        return Optional.empty();
    }

    /**
     * Checks if the player is near a specified location within a certain radius.
     *
     * @param x      the x-coordinate of the location.
     * @param y      the y-coordinate of the location.
     * @param z      the z-coordinate of the location.
     * @param radius the radius within which to search for the player.
     * @return {@code true} if the player is near the location, {@code false} otherwise.
     */
    @Override
    public boolean isNearByLocation(final double x, final double y, final double z, final double radius) {
        return this.player.getLocation().distance(new Location(this.player.getWorld(), x, y, z)) <= radius;
    }

    /**
     * Checks if the player is near a specified location within a certain radius.
     *
     * @param location the location to check.
     * @param radius   the radius within which to search for the player.
     * @return {@code true} if the player is near the location, {@code false} otherwise.
     */
    @Override
    public boolean isNearByLocation(final de.district.api.location.Location location, final double radius) {
        return this.player.getLocation().distance(location.toBukkitLocation()) <= radius;
    }
}
