package de.district.core.location.service;

import de.district.api.location.Location;
import de.district.api.location.interaction.Interactable;
import de.district.api.location.service.LocationService;
import de.district.core.DistrictRoleplay;
import de.district.core.location.repository.BaseLocationRepository;
import de.district.core.location.domain.BaseLocationEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The {@code CoreLocationService} class provides the core implementation of the {@link LocationService} interface,
 * offering various services related to locations within the system. This service handles the creation, retrieval,
 * updating, deletion, and interaction with locations.
 *
 * <p>This class is annotated with {@link Service}, making it a Spring-managed component that can be injected
 * wherever a {@code LocationService} is required. It uses a {@link BaseLocationRepository} to interact with the database
 * for location persistence.</p>
 *
 * <p>The service also manages interactions with locations through the use of {@link Interactable} instances,
 * enabling players to interact with different types of locations within the game environment.</p>
 *
 * @see LocationService
 * @see BaseLocationRepository
 * @see BaseLocationEntity
 * @see Interactable
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Service
public class CoreLocationService implements LocationService {

    @Autowired
    private BaseLocationRepository locationRepository;

    /**
     * Creates a new location in the system and persists it in the database.
     *
     * @param location the location to be created.
     * @return the created {@link Location} entity.
     * @throws ClassCastException if the provided location is not an instance of {@link BaseLocationEntity}.
     */
    @Override
    public @NotNull Location createLocation(@NotNull final Location location) {
        return locationRepository.save((BaseLocationEntity) location);
    }

    /**
     * Retrieves a location by its unique identifier.
     *
     * @param id the unique identifier of the location.
     * @return an {@link Optional} containing the location if found, or empty if not found.
     */
    @Override
    public Optional<Location> getLocationById(final long id) {
        return locationRepository.findById(id).map(location -> location);
    }

    /**
     * Retrieves all locations from the database.
     *
     * @return a list of all {@link Location} entities in the system.
     */
    @Override
    public @NotNull List<Location> getAllLocations() {
        return locationRepository.findAll().stream()
                .map(location -> (Location) location)
                .collect(Collectors.toList());
    }

    /**
     * Allows a player to interact with a specific location. The interactions are determined
     * by the type of location and the registered {@link Interactable} instances.
     *
     * @param player the player who is interacting with the location.
     * @param location the location with which the player is interacting.
     */
    @Override
    public void interactWithLocation(@NotNull final Player player, @NotNull final Location location) {
        List<Interactable> interactables = DistrictRoleplay.getInteractionHolder().interactions().stream()
                .filter(interactable -> interactable.getLocationType() == location.getType())
                .toList();
        interactables.forEach(interactable -> interactable.interact(player, location));
    }

    /**
     * Registers a new {@link Interactable} instance, allowing it to be associated with a location type.
     *
     * @param interactable the interactable instance to be registered.
     */
    @Override
    public void registerInteraction(@NotNull final Interactable interactable) {
        DistrictRoleplay.getInteractionHolder().addInteraction(interactable);
    }

    /**
     * Updates an existing location in the system.
     *
     * @param location the location to be updated.
     * @return the updated {@link Location} entity.
     * @throws ClassCastException if the provided location is not an instance of {@link BaseLocationEntity}.
     */
    @Override
    public @NotNull Location updateLocation(@NotNull final Location location) {
        return locationRepository.save((BaseLocationEntity) location);
    }

    /**
     * Deletes a location from the system by its unique identifier.
     *
     * @param id the unique identifier of the location to be deleted.
     */
    @Override
    public void deleteLocation(final long id) {
        locationRepository.deleteById(id);
    }
}
