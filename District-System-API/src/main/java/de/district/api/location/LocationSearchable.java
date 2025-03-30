package de.district.api.location;

import de.district.api.economy.GameAtm;
import de.district.api.economy.GameBank;

import java.util.List;
import java.util.Optional;

/**
 * The {@code LocationSearchable} interface provides methods for searching and locating in-game entities such as
 * banks and ATMs within a certain radius or finding the nearest one in the game world.
 *
 * <p>Implementations of this interface are expected to provide functionality to search for and retrieve
 * nearby {@link GameBank} and {@link GameAtm} entities based on the player's or another entity's location.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface LocationSearchable {

    /**
     * Finds all {@link GameBank} instances within the specified radius.
     *
     * @param radius the radius within which to search for nearby banks.
     * @return a list of nearby {@link GameBank} instances.
     */
    List<GameBank> findNearbyBanks(final double radius);

    /**
     * Finds the nearest {@link GameBank} to the current location.
     *
     * @return an {@link Optional} containing the nearest {@link GameBank}, or empty if none are found.
     */
    Optional<GameBank> findNearestBank();

    /**
     * Finds all {@link GameAtm} instances within the specified radius.
     *
     * @param radius the radius within which to search for nearby ATMs.
     * @return a list of nearby {@link GameAtm} instances.
     */
    List<GameAtm> findNearbyAtms(final double radius);

    /**
     * Finds the nearest {@link GameAtm} to the current location.
     *
     * @return an {@link Optional} containing the nearest {@link GameAtm}, or empty if none are found.
     */
    Optional<GameAtm> findNearestAtm();

    /**
     * Checks if the player is near a specified location within a certain radius.
     *
     * @param x      the x-coordinate of the location.
     * @param y      the y-coordinate of the location.
     * @param z      the z-coordinate of the location.
     * @param radius the radius within which to search for the player.
     * @return {@code true} if the player is near the location, {@code false} otherwise.
     */
    boolean isNearByLocation(double x, double y, double z, double radius);

    /**
     * Checks if the player is near a specified location within a certain radius.
     *
     * @param location the location to check.
     * @param radius   the radius within which to search for the player.
     * @return {@code true} if the player is near the location, {@code false} otherwise.
     */
    boolean isNearByLocation(Location location, double radius);
}
