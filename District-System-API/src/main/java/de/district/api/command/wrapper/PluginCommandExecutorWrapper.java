package de.district.api.command.wrapper;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.annotation.FactionRequired;
import de.district.api.command.annotation.Required;
import de.district.api.command.util.CommandUtil;
import de.district.api.entity.Console;
import de.district.api.entity.PluginPlayer;
import de.district.api.fail.exception.DistrictRoleplayException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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
        Required required = pluginCommandExecutor.getClass().getAnnotation(Required.class);
        FactionRequired factionRequired = pluginCommandExecutor.getClass().getAnnotation(FactionRequired.class);

        if (sender instanceof Player) {
            PluginPlayer pluginPlayer = DistrictAPI.getPluginPlayer((Player) sender);
            if (pluginPlayer == null) {
                throw new DistrictRoleplayException("An unexpected error occurred while trying to get the PluginPlayer instance.");
            }

            if (required != null && !pluginPlayer.hasPermission(required.permissions())) {
                pluginPlayer.sendMessage(Component.text("Du hast nicht die benötigten Rechte um diesen Befehl auszuführen.").color(TextColor.color(0xFF0000)));
                return false;
            }

            if (required != null && required.inAduty() && !pluginPlayer.isAduty()) {
                pluginPlayer.sendMessage(Component.text("Du musst im Aduty sein um diesen Befehl auszuführen.").color(TextColor.color(0xFF0000)));
                return false;
            }

            if (factionRequired != null && CommandUtil.inFaction(pluginPlayer, factionRequired)) {
                pluginPlayer.sendMessage(Component.text("Du bist nicht in der richtigen Fraktion um diesen Befehl auszuführen.").color(TextColor.color(0xFF0000)));
                return false;
            }

            if (factionRequired != null && !CommandUtil.minLevel(pluginPlayer, factionRequired.minLevel())) {
                pluginPlayer.sendMessage(Component.text("Du hast nicht das benötigte Level um diesen Befehl auszuführen.").color(TextColor.color(0xFF0000)));
                return false;
            }

            if (factionRequired != null && factionRequired.onlyLeader() && !CommandUtil.isLeader(pluginPlayer)) {
                pluginPlayer.sendMessage(Component.text("Du musst der Anführer deiner Fraktion sein um diesen Befehl auszuführen.").color(TextColor.color(0xFF0000)));
                return false;
            }

            return pluginCommandExecutor.onCommand(pluginPlayer, command, label, args);
        } else {
            Console console = DistrictAPI.getConsole();
            if (required != null && !required.useInConsole()) {
                console.sendMessage(Component.text("This command can only be executed by a player.").color(TextColor.color(0xFF0000)));
                return false;
            }
            return pluginCommandExecutor.onCommand(console, command, label, args);
        }
    }
}
