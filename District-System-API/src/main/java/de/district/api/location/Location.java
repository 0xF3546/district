package de.district.api.location;

import org.jetbrains.annotations.NotNull;

/**
 * The {@code Location} interface defines the contract for representing a location within the system.
 * Locations are fundamental components that define specific points in a world, characterized by
 * coordinates, a name, and a type.
 *
 * <p>Implementations of this interface are used to manage and interact with locations in the
 * game environment, such as defining specific areas or points of interest within a world.</p>
 *
 * @author Erik Pförtner
 * @see LocationType
 * @since 1.0.0
 */
public interface Location {

    /**
     * Retrieves the unique identifier for this location.
     *
     * @return the unique identifier of the location, or {@code null} if the location has not been persisted.
     */
    Long getId();

    /**
     * Retrieves the name of this location.
     *
     * @return the name of the location, never {@code null}.
     */
    @NotNull
    String getName();

    /**
     * Retrieves the X coordinate of this location.
     *
     * @return the X coordinate of the location.
     */
    double getX();

    /**
     * Retrieves the Y coordinate of this location.
     *
     * @return the Y coordinate of the location.
     */
    double getY();

    /**
     * Retrieves the Z coordinate of this location.
     *
     * @return the Z coordinate of the location.
     */
    double getZ();

    /**
     * Retrieves the name of the world in which this location is situated.
     *
     * @return the world name, never {@code null}.
     */
    @NotNull
    String getWorld();

    /**
     * Retrieves the type of this location.
     *
     * @return the {@link LocationType} of the location, never {@code null}.
     */
    @NotNull
    LocationType getType();
}
