package de.district.api.economy;

import de.district.api.location.Location;

/**
 * The {@code GameBank} interface represents an in-game bank within the economy system.
 * It provides a method to retrieve the bank's location in the game world.
 *
 * <p>Implementations of this interface are expected to handle the functionality related to in-game banks,
 * particularly in tracking their location.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface GameBank {

    /**
     * Retrieves the {@link Location} of this bank within the game.
     *
     * @return the location of the bank.
     */
    Location getLocation();
}
