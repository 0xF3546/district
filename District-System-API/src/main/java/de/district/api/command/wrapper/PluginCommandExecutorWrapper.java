package de.district.api.command.wrapper;

import de.district.api.DistrictRoleplayAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.entity.Console;
import de.district.api.entity.PluginPlayer;
import de.district.api.fail.exception.DistrictRoleplayException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PluginCommandExecutorWrapper implements CommandExecutor {

    private final PluginCommandExecutor pluginCommandExecutor;

    public PluginCommandExecutorWrapper(@NotNull final PluginCommandExecutor pluginCommandExecutor) {
        this.pluginCommandExecutor = pluginCommandExecutor;
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (sender instanceof Player) {
            PluginPlayer pluginPlayer = DistrictRoleplayAPI.getPluginPlayer((Player) sender);
            if (pluginPlayer == null) {
                throw new DistrictRoleplayException("An unexpected error occurred while trying to get the PluginPlayer instance.");
            }
            return pluginCommandExecutor.onCommand(pluginPlayer, command, label, args);
        } else {
            Console console = DistrictRoleplayAPI.getConsole();
            return pluginCommandExecutor.onCommand(console, command, label, args);
        }
    }
}
