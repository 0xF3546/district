package de.district.api.location.service;

import de.district.api.location.Location;
import de.district.api.location.interaction.Interactable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

/**
 * The {@code LocationService} interface defines the contract for managing locations within the system.
 * It provides methods for creating, retrieving, updating, deleting, and interacting with locations.
 *
 * <p>Implementations of this interface are responsible for handling the persistence of location data,
 * as well as managing interactions between players and locations in the game environment.</p>
 *
 * @author Erik Pförtner
 * @see Location
 * @see Interactable
 * @since 1.0.0
 */
public interface LocationService {

    /**
     * Creates a new location in the system.
     *
     * @param location the location to be created, must not be {@code null}.
     * @return the created {@link Location} entity.
     */
    @NotNull
    Location createLocation(@NotNull final Location location);

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id the unique identifier of the location.
     * @return an {@link Optional} containing the location if found, or empty if not found.
     */
    Optional<Location> getLocationById(final long id);

    /**
     * Retrieves all locations from the system.
     *
     * @return a list of all {@link Location} entities, never {@code null}.
     */
    @NotNull
    List<Location> getAllLocations();

    /**
     * Allows a player to interact with a specific location.
     *
     * <p>The interaction is determined by the type of location and the registered {@link Interactable}
     * instances that correspond to that location type.</p>
     *
     * @param player   the player who is interacting with the location, must not be {@code null}.
     * @param location the location with which the player is interacting, must not be {@code null}.
     */
    void interactWithLocation(@NotNull final Player player, @NotNull final Location location);

    /**
     * Registers a new {@link Interactable} instance, allowing it to be associated with a location type.
     *
     * @param interactable the interactable instance to be registered, must not be {@code null}.
     */
    void registerInteraction(@NotNull final Interactable interactable);

    /**
     * Updates an existing location in the system.
     *
     * @param location the location to be updated, must not be {@code null}.
     * @return the updated {@link Location} entity.
     */
    @NotNull
    Location updateLocation(@NotNull final Location location);

    /**
     * Deletes a location from the system by its unique identifier.
     *
     * @param id the unique identifier of the location to be deleted.
     */
    void deleteLocation(final long id);
}
