package de.district.api.location.interaction;

import de.district.api.location.Location;
import de.district.api.location.LocationType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code Interactable} class serves as an abstract base class for defining interactions that can occur
 * at specific locations within the game world.
 * Each interactable object is associated with a specific
 * {@link LocationType}, and subclasses must implement the interaction logic.
 *
 * <p>This class is intended to be extended by concrete subclasses that define the specific behavior when
 * a player interacts with a location of a given type.</p>
 *
 * @author Erik Pförtner
 * @see Location
 * @see LocationType
 * @since 1.0.0
 */
public abstract class Interactable {

    private final LocationType locationType;

    /**
     * Constructs a new {@code Interactable} associated with a specific {@link LocationType}.
     *
     * @param locationType the type of location this interactable is associated with, must not be {@code null}.
     */
    protected Interactable(@NotNull final LocationType locationType) {
        this.locationType = locationType;
    }

    /**
     * Retrieves the {@link LocationType} associated with this interactable.
     *
     * @return the location type, never {@code null}.
     */
    public @NotNull LocationType getLocationType() {
        return locationType;
    }

    /**
     * Defines the interaction that occurs when a player interacts with a specific location.
     *
     * <p>This method must be implemented by subclasses to define the specific interaction logic.
     * The interaction typically involves the player performing some action at the specified location.</p>
     *
     * @param player   the player interacting with the location, must not be {@code null}.
     * @param location the location being interacted with, must not be {@code null}.
     */
    public abstract void interact(@NotNull final Player player, @NotNull final Location location);
}
