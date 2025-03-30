package de.district.core.admin.command;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.api.util.Prefix;
import de.district.core.admin.service.AdminService;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class KickCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(Component.text("Der Spieler wurde nicht gefunden."), Prefix.ERROR);
            return false;
        }
        StringBuilder reason = new StringBuilder(args[1]);
        for (int i = 2; i < args.length; i++) {
            reason.append(" ").append(args[i]);
        }
        PluginPlayer pluginPlayer = DistrictAPI.getPluginPlayer(targetPlayer);
        if (pluginPlayer == null) {
            player.sendMessage(Component.text("Der Spieler wurde nicht gefunden."), Prefix.ERROR);
            return false;
        }
        AdminService adminService = DistrictAPI.getBean(AdminService.class);
        adminService.kickPlayer(pluginPlayer, reason.toString());
        return false;
    }
}
