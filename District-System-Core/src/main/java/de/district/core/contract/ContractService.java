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
import org.springframework.stereotype.Service;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class ContractService {
    private final ObjectList<PlayerContract> contracts = new ObjectArrayList<>();

    public void addContract(final PlayerContract contract) {
        this.contracts.add(contract);
    }

    public void removeContract(final PlayerContract contract) {
        this.contracts.remove(contract);
    }

    public ObjectList<PlayerContract> getContracts() {
        return this.contracts;
    }

    private PlayerContract getContract(final Player player) {
        return this.contracts.stream().filter(contract -> contract.getContractor().equals(player) || contract.getTarget().equals(player)).findFirst().orElse(null);
    }

    public void handleAccept(final Player player) {
        final PlayerContract contract = getContract(player);
        if (contract != null) {
            contract.getAcceptCallback().execute();
            removeContract(contract);
        }
    }

    public void handleDecline(final Player player) {
        final PlayerContract contract = getContract(player);
        if (contract != null) {
            contract.getDeclineCallback().execute();
            removeContract(contract);
        }
    }

    public void sendInfoMessage(PluginPlayer player) {
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
