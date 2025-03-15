package de.district.api.contract;

import de.district.api.util.ExecutableCallback;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code PlayerContract} interface represents a contract between two players.
 * This interface provides methods to interact with the contract, including retrieving
 * the contractor, the target, the type, and the callbacks for accepting or declining the contract.
 *
 * <p>Implementations of this interface are expected to manage contracts between players in a game environment.</p>
 *
 * @author Mayson1337, Erik Pförtner
 * @since 1.0.0
 */
public interface PlayerContract {

    /**
     * Retrieves the player who initiated the contract.
     *
     * @return the player who initiated the contract, never {@code null}.
     */
    @NotNull
    Player getContractor();

    /**
     * Retrieves the player who is the target of the contract.
     *
     * @return the player who is the target of the contract, never {@code null}.
     */
    @NotNull
    Player getTarget();

    /**
     * Retrieves the type of the contract.
     *
     * @return the type of the contract, never {@code null}.
     */
    @NotNull
    String getType();

    /**
     * Retrieves the callback that is executed when the contract is accepted.
     *
     * @return the callback that is executed when the contract is accepted, never {@code null}.
     */
    @NotNull
    ExecutableCallback getAcceptCallback();

    /**
     * Retrieves the callback that is executed when the contract is declined.
     *
     * @return the callback that is executed when the contract is declined, never {@code null}.
     */
    @NotNull
    ExecutableCallback getDeclineCallback();
}
