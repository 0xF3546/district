package de.district.api.economy;

import de.district.api.location.Location;

/**
 * The {@code GameAtm} interface represents an in-game ATM (Automated Teller Machine) within the economy system.
 * It provides methods to retrieve the ATM's location and the amount of money currently available at the ATM.
 *
 * <p>Implementations of this interface are expected to handle the functionality related to in-game ATMs,
 * such as tracking their location and managing their available cash reserves.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface GameAtm {

    /**
     * Retrieves the {@link Location} of this ATM within the game.
     *
     * @return the location of the ATM.
     */
    Location getLocation();

    /**
     * Retrieves the amount of money currently available at this ATM.
     *
     * @return the available money at the ATM.
     */
    double getAvailableMoney();
}
