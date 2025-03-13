package de.district.core.admin.command;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.core.admin.service.AdminService;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class AdutyCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("DU hurensohn");
            return false;
        }
        AdminService adminService = DistrictAPI.getBean(AdminService.class);
        adminService.togglePlayerAduty(player);
        return false;
    }
}
