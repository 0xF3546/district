package de.district.core.contract;

import de.district.api.contract.PlayerContract;
import de.district.api.util.ExecutableCallback;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code Contract} class represents a contract between two players.
 * This class provides methods to interact with the contract, including retrieving
 * the contractor, the target, the type, and the callbacks for accepting or declining the contract.
 *
 * <p>Implementations of this class are expected to manage contracts between players in a game environment.</p>
 *
 * @since 1.0.0
 * @author Mayson1337, Erik Pförtner
 */
public class Contract implements PlayerContract {

    /**
     * The player who initiated the contract.
     */
    @NotNull
    @Getter
    private final Player contractor;

    /**
     * The player who is the target of the contract.
     */
    @NotNull
    @Getter
    private final Player target;

    /**
     * The type of the contract.
     */
    @NotNull
    @Getter
    private final String type;

    /**
     * The callback that is executed when the contract is accepted.
     */
    @NotNull
    @Getter
    private final ExecutableCallback acceptCallback;

    /**
     * The callback that is executed when the contract is declined.
     */
    @NotNull
    @Getter
    private final ExecutableCallback declineCallback;

    /**
     * Constructs a new {@code Contract} with the specified contractor, target, type, and callbacks.
     *
     * @param contractor the player who initiated the contract, not {@code null}.
     * @param target the player who is the target of the contract, not {@code null}.
     * @param type the type of the contract, not {@code null}.
     * @param acceptCallback the callback that is executed when the contract is accepted, not {@code null}.
     * @param declineCallback the callback that is executed when the contract is declined, not {@code null}.
     */
    public Contract(@NotNull final Player contractor,
                    @NotNull final Player target,
                    @NotNull final String type,
                    @NotNull final ExecutableCallback acceptCallback,
                    @NotNull final ExecutableCallback declineCallback) {
        this.contractor = contractor;
        this.target = target;
        this.type = type;
        this.acceptCallback = acceptCallback;
        this.declineCallback = declineCallback;
    }
}
