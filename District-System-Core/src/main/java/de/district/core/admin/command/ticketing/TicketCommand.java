package de.district.core.admin.command.ticketing;

import de.district.api.DistrictAPI;
import de.district.api.admin.PlayerTicket;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.api.util.Prefix;
import de.district.api.util.Utils;
import de.district.core.admin.Ticket;
import de.district.core.admin.service.AdminService;
import de.district.core.admin.service.TicketService;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class TicketCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        TicketService ticketService = DistrictAPI.getBean(TicketService.class);
        if (ticketService.getByPlayer(player.getName()) != null) {
            player.sendMessage(Component.text("§cDu hast bereits ein Ticket erstellt."), Prefix.TICKET);
            return false;
        }
        if (args.length == 0) {
            player.sendMessage("§c/ticket [Anliegen]");
            return false;
        }
        AdminService adminService = DistrictAPI.getBean(AdminService.class);

        final String reason = String.join(" ", args);
        PlayerTicket ticket = new Ticket(player, reason, Utils.getTime());
        ticketService.addTicket(ticket);
        player.sendMessage(Component.text("Du hast ein Ticket erstellt."), Prefix.TICKET);
        adminService.sendGuideMessage("Ein neues Ticket wurde erstellt.", Color.BLUE);
        return false;
    }
}
