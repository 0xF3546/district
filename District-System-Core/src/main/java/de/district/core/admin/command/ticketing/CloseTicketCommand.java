package de.district.core.admin.command.ticketing;

import de.district.api.DistrictAPI;
import de.district.api.admin.PlayerTicket;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.api.util.Prefix;
import de.district.core.admin.service.TicketService;
import it.unimi.dsi.fastutil.objects.ObjectList;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class CloseTicketCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        TicketService ticketService = DistrictAPI.getBean(TicketService.class);
        PlayerTicket ticket = ticketService.getByPlayer(player.getName());
        if (ticket == null) {
            player.sendMessage(Component.text("§cDu bist in keinem Ticket."), Prefix.TICKET);
            return false;
        }
        ObjectList<PluginPlayer> participants = ticket.getParticipants();
        participants.add(ticket.getCreator());
        participants.forEach(participant -> participant.sendMessage(Component.text("§cDas Ticket wurde von " + player.getName() + " geschlossen."), Prefix.TICKET));
        ticketService.removeTicket(ticket);
        return false;
    }
}
