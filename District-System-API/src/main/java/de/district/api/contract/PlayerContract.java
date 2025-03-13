package de.district.api.contract;

import de.district.api.util.ExecutableCallback;
import org.bukkit.entity.Player;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PlayerContract {

    /**
     * @return the contractor
     */
    Player getContractor();

    /**
     * @return the target
     */
    Player getTarget();

    /**
     * @return the type
     */
    String getType();

    /**
     * @return the accept callback
     */
    ExecutableCallback getAcceptCallback();

    /**
     * @return the decline callback
     */
    ExecutableCallback getDeclineCallback();
}
