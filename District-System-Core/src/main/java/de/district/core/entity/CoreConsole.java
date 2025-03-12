package de.district.core.entity;

import de.district.api.entity.Console;
import de.district.core.DistrictRoleplay;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public class CoreConsole implements Console {

    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @Override
    public void sendMessage(final @NotNull String message) {
        this.console.sendMessage(message);
    }

    @Override
    public void sendMessage(final @NotNull String message, final boolean prefix) {
        if (prefix) {
            this.console.sendMessage(DistrictRoleplay.PREFIX + message);
        } else {
            this.console.sendMessage(message);
        }
    }

    @Override
    public void sendMessage(final @NotNull Component message, final boolean prefix) {
        if (prefix) {
            this.console.sendMessage(Component.text(DistrictRoleplay.PREFIX).append(message));
        } else {
            this.console.sendMessage(message);
        }
    }
}
