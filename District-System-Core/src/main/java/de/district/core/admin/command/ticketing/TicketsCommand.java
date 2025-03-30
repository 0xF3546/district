package de.district.core.admin.command.ticketing;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.core.admin.service.TicketService;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class TicketsCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        TicketService ticketService = DistrictAPI.getBean(TicketService.class);
        player.sendMessage(Component.text("§8   ===§7[§6Tickets§7]§8==="));
        ticketService.getTickets().forEach(ticket -> {
            player.sendMessage(Component.text("§7- §6" + ticket.getCreator().getName() + " §8| §7" + ticket.getReason()));
        });
        return false;
    }
}
