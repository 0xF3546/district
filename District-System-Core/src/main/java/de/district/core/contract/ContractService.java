package de.district.core.contract;

import de.district.api.contract.PlayerContract;
import de.district.api.entity.PluginPlayer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

/**
 * The {@code ContractService} class manages a collection of {@link PlayerContract}
 * objects.
 * It provides methods for contract operations such as adding, removing,
 * accepting, declining, and retrieving contracts.
 * This service facilitates the
 * interaction between players in a game environment through contracts.
 * <p>
 * This class also provides functionality to send in-game information messages
 * to players regarding actions they can take related to contracts.
 * </p>
 *
 * @author Mayson1337, Erik Pförtner
 * @since 1.0.0
 */
@Service
public class ContractService {
    /**
     * The list of {@link PlayerContract} objects.
     */
    private final ObjectList<PlayerContract> contracts = new ObjectArrayList<>();

    /**
     * Adds a {@link PlayerContract} object to the list of contracts.
     *
     * @param contract the contract to add
     */
    public void addContract(@NotNull final PlayerContract contract) {
        this.contracts.add(contract);
    }

    /**
     * Removes a {@link PlayerContract} object from the list of contracts.
     *
     * @param contract the contract to remove
     */
    public void removeContract(@NotNull final PlayerContract contract) {
        this.contracts.remove(contract);
    }

    /**
     * Retrieves the list of {@link PlayerContract} objects.
     *
     * @return the list of contracts
     */
    @NotNull
    public ObjectList<PlayerContract> getContracts() {
        return this.contracts;
    }

    /**
     * Retrieves a {@link PlayerContract} object from the list of contracts.
     *
     * @param player the player to retrieve the contract for
     * @return the contract for the player
     */
    @Nullable
    private PlayerContract getContract(@NotNull final Player player) {
        return this.contracts.stream().filter(contract -> contract.getContractor().equals(player) || contract.getTarget().equals(player)).findFirst().orElse(null);
    }

    /**
     * Handles the acceptance of a contract by a player.
     *
     * @param player the player who accepted the contract
     */
    public void handleAccept(@NotNull final Player player) {
        final PlayerContract contract = getContract(player);
        if (contract != null) {
            contract.getAcceptCallback().execute();
            removeContract(contract);
        }
    }

    /**
     * Handles the declination of a contract by a player.
     *
     * @param player the player who declined the contract
     */
    public void handleDecline(@NotNull final Player player) {
        final PlayerContract contract = getContract(player);
        if (contract != null) {
            contract.getDeclineCallback().execute();
            removeContract(contract);
        }
    }

    /**
     * Sends an information message to a player regarding the actions they can take
     * related to a contract.
     *
     * @param player the player to send the message to
     */
    public void sendInfoMessage(@NotNull final PluginPlayer player) {
        Component accept = Component.text("/annehmen")
                .color(NamedTextColor.GREEN)
                .clickEvent(ClickEvent.runCommand("/annehmen"))
                .hoverEvent(HoverEvent.showText(Component.text("Annehmen").color(NamedTextColor.GREEN).decorate(TextDecoration.ITALIC)));

        Component decline = Component.text("/ablehnen")
                .color(NamedTextColor.RED)
                .clickEvent(ClickEvent.runCommand("/ablehnen"))
                .hoverEvent(HoverEvent.showText(Component.text("Ablehnen").color(NamedTextColor.RED).decorate(TextDecoration.ITALIC)));

        Component message = Component.text(" ➥ Nutze ", NamedTextColor.DARK_GRAY)
                .append(accept)
                .append(Component.text(" oder ", NamedTextColor.GRAY))
                .append(decline)
                .append(Component.text(".", NamedTextColor.GRAY));

        player.sendMessage(message);
    }
}
