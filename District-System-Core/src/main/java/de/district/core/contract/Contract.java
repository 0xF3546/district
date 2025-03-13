package de.district.core.contract;

import de.district.api.contract.PlayerContract;
import de.district.api.util.ExecutableCallback;
import lombok.Getter;
import org.bukkit.entity.Player;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class Contract implements PlayerContract {

    @Getter
    private final Player contractor;

    @Getter
    private final Player target;

    @Getter
    private final String type;

    @Getter
    private final ExecutableCallback acceptCallback;

    @Getter
    private final ExecutableCallback declineCallback;

    public Contract(final Player contractor, final Player target, final String type, final ExecutableCallback acceptCallback, final ExecutableCallback declineCallback) {
        this.contractor = contractor;
        this.target = target;
        this.type = type;
        this.acceptCallback = acceptCallback;
        this.declineCallback = declineCallback;
    }
}
