package de.district.api;

import de.district.api.collectors.SystemCollector;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginCommandSender;
import de.district.api.command.PluginTabCompleter;
import de.district.api.entity.Console;
import de.district.api.entity.PluginOfflinePlayer;
import de.district.api.entity.PluginPlayer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The {@code Server} interface defines the contract for accessing various
 * server-level resources and services in a modular and extensible manner.
 *
 * <p>This interface is a central point of interaction for obtaining instances
 * of important services such as logging, plugin data management, system information
 * collection, and versioning within a Minecraft-based server environment.</p>
 *
 * <p>Implementations of this interface should ensure that the methods return
 * the appropriate resources or services configured for the server. This
 * includes providing access to Spring-managed beans through the {@link #getBean(Class)}
 * method, which facilitates the retrieval of Spring components by type.</p>
 *
 * <p>This interface is designed to be implemented within the core module of the
 * application, with the expectation that the API module remains agnostic of the
 * core's implementation details.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public interface Server {

    /**
     * Retrieves the {@link Logger} instance used by the plugin for logging purposes.
     *
     * <p>This logger is typically configured with handlers and formatters appropriate
     * for the server's logging system and should be used for all plugin-related logging
     * activities.</p>
     *
     * @return a non-null {@link Logger} instance specific to the plugin.
     */
    @NotNull
    Logger getPluginLogger();

    /**
     * Retrieves the folder where the plugin's data files are stored.
     *
     * <p>This folder is the designated location for storing any data files
     * the plugin may need to persist across server restarts, such as configurations,
     * logs, or player data. The folder is created if it does not already exist.</p>
     *
     * @return a non-null {@link File} representing the plugin's data folder.
     */
    @NotNull
    File getPluginDataFolder();

    /**
     * Retrieves the {@link SystemCollector} instance that provides access
     * to various system metrics and properties.
     *
     * <p>The {@code SystemCollector} is responsible for collecting and
     * storing information such as the operating system details, JVM metrics,
     * and memory usage statistics, which can be used for monitoring and
     * diagnostic purposes.</p>
     *
     * @return a non-null {@link SystemCollector} instance.
     */
    @NotNull
    SystemCollector getSystemCollector();

    /**
     * Retrieves the {@link MinecraftVersion} instance representing the
     * version of Minecraft the server is running.
     *
     * <p>This method provides versioning information, which can be useful
     * for ensuring compatibility with specific Minecraft features or APIs.</p>
     *
     * @return a non-null {@link MinecraftVersion} instance.
     */
    @NotNull
    MinecraftVersion getMinecraftVersion();

    /**
     * Retrieves a Spring-managed bean of the specified type from the application context.
     *
     * <p>This method allows the server to access beans managed by the Spring
     * Framework, enabling integration with various Spring components and services.
     * The type of the bean is specified by the {@code clazz} parameter, and the method
     * returns the bean instance if it is available in the context.</p>
     *
     * <p>This method is especially useful for accessing shared services, utilities, or
     * other components that are defined in the Spring context but are required by the
     * server at runtime.</p>
     *
     * @param <T>   the type of the bean to be retrieved.
     * @param clazz the {@link Class} object representing the type of the bean.
     * @return the Spring-managed bean of the specified type.
     */
    <T> T getBean(@NotNull final Class<T> clazz);

    /**
     * Retrieves the {@link PluginPlayer} instance associated with the specified player.
     *
     * <p>This method returns the {@link PluginPlayer} instance that corresponds to the
     * given {@link Player} object, allowing the server to access and manage player-specific
     * data and functionality. If the player is not found or does not have an associated
     * {@link PluginPlayer} instance, the method returns {@code null}.</p>
     *
     * @param player the {@link Player} object for which to retrieve the {@link PluginPlayer}.
     * @return the {@link PluginPlayer} instance associated with the player, or {@code null} if not found.
     */
    @Nullable
    PluginPlayer getPluginPlayer(@NotNull final Player player);

    @Nullable
    PluginPlayer getPluginPlayer(@NotNull final String name);

    @NotNull
    PluginPlayer getPluginPlayer(@NotNull final UUID uuid);

    /**
     * Retrieves the {@link PluginOfflinePlayer} instance associated with the specified offline player.
     *
     * <p>This method returns the {@link PluginOfflinePlayer} instance that corresponds to the
     * given {@link OfflinePlayer} object, allowing the server to access and manage offline player-specific
     * data and functionality. If the player is not found or does not have an associated
     * {@link PluginOfflinePlayer} instance, the method returns {@code null}.</p>
     *
     * @param player the {@link OfflinePlayer} object for which to retrieve the {@link PluginOfflinePlayer}.
     * @return the {@link PluginOfflinePlayer} instance associated with the offline player, or {@code null} if not found.
     */
    @Nullable
    PluginOfflinePlayer getPluginOfflinePlayer(@NotNull final OfflinePlayer player);

    /**
     * Retrieves the {@link Console} instance representing the server console.
     *
     * <p>This method provides access to the server console, allowing the plugin to interact
     * with the console output and send messages to the server console. The console instance
     * should be used for logging messages and other console-related activities.</p>
     *
     * @return the {@link Console} instance representing the server console.
     */
    Console getConsole();

    /**
     * Registers a plugin command with the specified name and executor.
     *
     * <p>This method registers a plugin command with the server, associating the
     * specified command name with the provided {@link PluginCommandExecutor} instance.
     * When the command is executed by a player or the console, the executor's
     * {@link PluginCommandExecutor#onCommand(PluginCommandSender, Command, String, String[])}
     * method is called to handle the command logic.</p>
     *
     * @param plugin   the {@link JavaPlugin} instance that owns the command.
     * @param name     the name of the command to register.
     * @param executor the {@link PluginCommandExecutor} instance to handle the command execution.
     */
    void registerPluginCommand(@NotNull JavaPlugin plugin, @NotNull final String name, @NotNull final PluginCommandExecutor executor);

    /**
     * Registers a plugin tab completer with the specified name and tab completer.
     *
     * <p>This method registers a plugin tab completer with the server, associating the
     * specified command name with the provided {@link PluginTabCompleter} instance.
     * When a player or the console uses the command, the tab completer's
     * {@link PluginTabCompleter#onTabComplete(PluginCommandSender, Command, String, String[])}
     * method is called to provide tab completion suggestions.</p>
     *
     * @param plugin       the {@link JavaPlugin} instance that owns the tab completer.
     * @param name         the name of the command to register the tab completer for.
     * @param tabCompleter the {@link PluginTabCompleter} instance to handle tab completion.
     */
    void registerPluginTabCompleter(@NotNull final JavaPlugin plugin, @NotNull final String name, @NotNull final PluginTabCompleter tabCompleter);
}
