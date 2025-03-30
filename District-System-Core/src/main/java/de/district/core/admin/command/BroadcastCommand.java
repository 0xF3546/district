package de.district.core.admin.command;

import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.entity.PluginPlayer;
import de.district.api.util.Prefix;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class BroadcastCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            sender.sendMessage("§cYou must be a player to execute this command.");
            return false;
        }
        if (args.length == 0) {
            player.sendMessage(Component.text("§c/broadcast [Nachricht]"), Prefix.ERROR);
            return false;
        }
        StringBuilder message = new StringBuilder();
        return false;
    }
}
