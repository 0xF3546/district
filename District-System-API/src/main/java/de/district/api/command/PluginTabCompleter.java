package de.district.api.command;

import org.bukkit.command.Command;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface PluginTabCompleter {

    @Nullable
    List<String> onTabComplete(@NotNull final PluginCommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args);
}
