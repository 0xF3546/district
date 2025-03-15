package de.district.core.admin.command.ticketing;

import de.district.api.DistrictAPI;
import de.district.api.admin.PlayerTicket;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.api.util.Prefix;
import de.district.core.admin.service.TicketService;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class AcceptTicketCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        TicketService ticketService = DistrictAPI.getBean(TicketService.class);
        PlayerTicket ticket = ticketService.getByPlayer(player.getName());
        if (ticket != null) {
            player.sendMessage(Component.text("§cDu bearbeitest bereits ein Ticket."), Prefix.TICKET);
            return false;
        }
        if (args.length < 1) {
            player.sendMessage(Component.text("§c/accept [Spieler]"), Prefix.ERROR);
            return false;
        }
        PluginPlayer target = DistrictAPI.getPluginPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Component.text("§cDer Spieler ist nicht online."), Prefix.ERROR);
            return false;
        }

        ticket = ticketService.getByPlayer(target.getName());
        if (ticket == null) {
            player.sendMessage(Component.text("§cDer Spieler hat kein Ticket erstellt."), Prefix.ERROR);
            return false;
        }

        ticket.addParticipant(player);
        player.sendMessage(Component.text("Du hast das Ticket von " + target.getName() + " angenommen."), Prefix.TICKET);
        target.sendMessage(Component.text("§cDein Ticket wurde von " + player.getName() + " angenommen."), Prefix.TICKET);
        return false;
    }
}
