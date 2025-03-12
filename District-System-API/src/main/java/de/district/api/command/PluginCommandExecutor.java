package de.district.api.command;

import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;

public interface PluginCommandExecutor {

    boolean onCommand(@NotNull PluginCommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args);
}
