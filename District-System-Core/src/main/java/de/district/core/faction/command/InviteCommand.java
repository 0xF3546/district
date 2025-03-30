package de.district.core.faction.command;

import de.district.api.DistrictAPI;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.contract.PlayerContract;
import de.district.api.entity.PluginPlayer;
import de.district.core.contract.Contract;
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
public class InviteCommand implements PluginCommandExecutor {
    @Override
    public boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof PluginPlayer player)) {
            return false;
        }
        Player targetPlayer = Bukkit.getPlayer(args[0]);
        if (targetPlayer == null) {
            player.sendMessage(Component.text("Spieler nicht gefunden"));
            return false;
        }
        PluginPlayer target = DistrictAPI.getPluginPlayer(targetPlayer);
        PlayerContract contract = new Contract(targetPlayer, player.getBukkitPlayer(), "FactionInvite",
                () -> {
                    player.sendMessage(Component.text("Der Spieler hat die Einladung angenommen"));
                },
                () -> {
                    player.sendMessage(Component.text("Der Spieler hat die Einladung abgelehnt"));
                });
        return false;
    }
}
