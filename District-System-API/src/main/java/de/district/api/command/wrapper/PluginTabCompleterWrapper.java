package de.district.api.command.wrapper;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginTabCompleter;
import de.district.api.entity.Console;
import de.district.api.entity.PluginPlayer;
import de.district.api.fail.exception.DistrictRoleplayException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PluginTabCompleterWrapper implements TabCompleter {
    private final PluginTabCompleter pluginTabCompleter;

    public PluginTabCompleterWrapper(@NotNull final PluginTabCompleter pluginTabCompleter) {
        this.pluginTabCompleter = pluginTabCompleter;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, final @NotNull String[] args) {
        if (sender instanceof Player player) {
            PluginPlayer pluginPlayer = DistrictAPI.getPluginPlayer(player);
            if (pluginPlayer == null) {
                throw new DistrictRoleplayException("An unexpected error occurred while trying to get the PluginPlayer instance.");
            }
            return pluginTabCompleter.onTabComplete(pluginPlayer, command, label, args);
        } else {
            Console console = DistrictAPI.getConsole();
            return pluginTabCompleter.onTabComplete(console, command, label, args);
        }
    }
}
