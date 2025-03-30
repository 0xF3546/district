package de.district.api;

import de.district.api.collectors.SystemCollector;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.PluginTabCompleter;
import de.district.api.entity.Console;
import de.district.api.entity.PluginOfflinePlayer;
import de.district.api.entity.PluginPlayer;
import de.splatgames.aether.permissions.api.PermissionManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * The {@code DistrictAPI} class provides a static entry point for accessing core
 * services and components of the District-Roleplay plugin. This class serves as a utility
 * for interacting with the plugin's core features without directly depending on specific
 * implementation details.
 *
 * <p>This class follows the Singleton design pattern, ensuring that only one instance of
 * the {@link Server} implementation is set during the plugin's lifecycle. Once the server
 * instance is set, it cannot be changed, preventing unintended re-initialization.</p>
 *
 * <p>The {@code DistrictAPI} class cannot be instantiated and should be accessed
 * statically.</p>
 *
 * @author Erik Pförtner
 * @see Server
 * @since 1.0.0
 */
public final class DistrictAPI {
    private static Server server;

    /**
     * Private constructor to prevent instantiation of the {@code DistrictAPI} class.
     *
     * <p>This constructor throws an {@link UnsupportedOperationException} to enforce
     * that the class cannot be instantiated.</p>
     */
    private DistrictAPI() {
        throw new UnsupportedOperationException("API cannot be instantiated");
    }

    /**
     * Sets the {@link Server} instance that will be used by the {@code DistrictAPI}
     * to access core services and components.
     *
     * <p>This method can only be called once during the plugin's lifecycle. Attempting
     * to set the server instance more than once will result in an {@link IllegalStateException}.</p>
     *
     * @param server the {@link Server} instance to set, must not be {@code null}.
     * @throws IllegalStateException if the server instance has already been set.
     */
    public static void setServer(@NotNull final Server server) {
        if (DistrictAPI.server != null) {
            throw new IllegalStateException("Cannot instantiate singleton twice");
        }

        DistrictAPI.server = server;
    }

    public static void flushServer() {
        String callerClassName = new Exception().getStackTrace()[1].getClassName();
        if (!callerClassName.equals("de.district.core.DistrictRoleplay")) {
            throw new IllegalStateException("Cannot flush server from outside the plugin");
        }
        server = null;
    }

    /**
     * Retrieves the {@link Logger} associated with the plugin.
     *
     * @return a non-null {@link Logger} instance for logging purposes.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static Logger getLogger() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginLogger();
    }

    /**
     * Retrieves the data folder associated with the plugin.
     *
     * @return a non-null {@link File} instance pointing to the plugin's data folder.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static File getDataFolder() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginDataFolder();
    }

    /**
     * Retrieves the {@link SystemCollector} instance used by the plugin to collect
     * and access various system metrics and properties.
     *
     * @return a non-null {@link SystemCollector} instance.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static SystemCollector getSystemCollector() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getSystemCollector();
    }

    /**
     * Retrieves the {@link MinecraftVersion} instance representing the
     * version of Minecraft the server is running.
     *
     * <p>This method provides versioning information, which can be useful
     * for ensuring compatibility with specific Minecraft features or APIs.</p>
     *
     * @return a non-null {@link MinecraftVersion} instance.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static MinecraftVersion getMinecraftVersion() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getMinecraftVersion();
    }

    /**
     * Retrieves the {@link PermissionManager} instance that provides access
     * to the permission system.
     *
     * <p>The {@code PermissionManager} is responsible for managing permissions
     * and groups, which can be used for managing permissions and groups.</p>
     *
     * @return a non-null {@link PermissionManager} instance.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static PermissionManager getPermissionManager() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPermissionManager();
    }

    /**
     * Retrieves a Spring-managed bean from the application context by its type.
     *
     * <p>This method provides access to Spring components and services used by the plugin.</p>
     *
     * @param <T>   the type of the bean to retrieve.
     * @param clazz the {@code Class} object representing the type of the bean.
     * @return the Spring-managed bean of the specified type.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @NotNull
    public static <T> T getBean(@NotNull final Class<T> clazz) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getBean(clazz);
    }

    /**
     * Retrieves a {@link PluginPlayer} instance for the specified {@link Player}.
     *
     * <p>This method provides access to the plugin's player data and properties.</p>
     *
     * @param player the {@link Player} to retrieve the {@link PluginPlayer} for.
     * @return a {@link PluginPlayer} instance for the specified player, or {@code null} if not found.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @Nullable
    public static PluginPlayer getPluginPlayer(@NotNull final Player player) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginPlayer(player);
    }

    @Nullable
    public static PluginPlayer getPluginPlayer(@NotNull final String name) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginPlayer(name);
    }

    @Nullable
    public static PluginPlayer getPluginPlayer(@NotNull final UUID uuid) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginPlayer(uuid);
    }

    /**
     * Retrieves a {@link PluginOfflinePlayer} instance for the specified {@link OfflinePlayer}.
     *
     * <p>This method provides access to the plugin's offline player data and properties.</p>
     *
     * @param player the {@link OfflinePlayer} to retrieve the {@link PluginOfflinePlayer} for.
     * @return a {@link PluginOfflinePlayer} instance for the specified player, or {@code null} if not found.
     * @throws IllegalStateException if the server instance has not been set.
     */
    @Nullable
    public static PluginOfflinePlayer getPluginOfflinePlayer(@NotNull final OfflinePlayer player) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getPluginOfflinePlayer(player);
    }

    /**
     * Retrieves the {@link Console} instance representing the server console.
     *
     * <p>The {@code Console} instance provides access to the server console, allowing
     * the plugin to interact with the console output and send messages to the console.</p>
     *
     * @return a non-null {@link Console} instance representing the server console.
     * @throws IllegalStateException if the server instance has not been set.
     */
    public static Console getConsole() {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        return server.getConsole();
    }

    /**
     * Registers a plugin command with the specified name and executor.
     *
     * <p>This method allows plugins to register custom commands with the server, providing
     * a custom executor to handle command execution logic.</p>
     *
     * @param plugin   the {@link JavaPlugin} registering the command.
     * @param name     the name of the command to register.
     * @param executor the {@link PluginCommandExecutor} to handle command execution.
     * @throws IllegalStateException if the server instance has not been set.
     */
    public static void registerPluginCommand(@NotNull final JavaPlugin plugin, @NotNull final String name, @NotNull final PluginCommandExecutor executor) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        server.registerPluginCommand(plugin, name, executor);
    }

    public static void registerPluginTabCompleter(@NotNull final JavaPlugin plugin, @NotNull final String name, @NotNull final PluginTabCompleter tabCompleter) {
        if (server == null) {
            throw new IllegalStateException("Server has not been initialized");
        }
        server.registerPluginTabCompleter(plugin, name, tabCompleter);
    }
}
