package de.district.core;

import de.district.api.DistrictRoleplayAPI;
import de.district.api.MinecraftVersion;
import de.district.api.Server;
import de.district.api.collectors.SystemCollector;
import de.district.api.command.PluginCommandExecutor;
import de.district.api.command.wrapper.PluginCommandExecutorWrapper;
import de.district.api.entity.Console;
import de.district.api.entity.PluginOfflinePlayer;
import de.district.api.entity.PluginPlayer;
import de.district.api.fail.exception.DistrictRoleplayException;
import de.district.core.collectors.CoreSystemCollector;
import de.district.core.config.PluginConfiguration;
import de.district.core.entity.CoreConsole;
import de.district.core.entity.CorePluginOfflinePlayer;
import de.district.core.entity.CorePluginPlayer;
import de.district.core.location.InteractionHolder;
import de.district.core.util.ConvertingUtils;
import de.splatgames.springlify.annotation.SpringlifyApplication;
import de.splatgames.springlify.plugin.SpringlifyBukkitPlugin;
import lombok.Getter;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code DistrictRoleplay} class is the main entry point for the District-Roleplay plugin within a Minecraft server environment.
 * It extends {@link SpringlifyBukkitPlugin} and implements the {@link Server} interface, providing essential server operations,
 * including lifecycle management, logging, and system metrics collection.
 *
 * <p>This class is annotated with {@link SpringlifyApplication}, which allows it to leverage the Spring Framework for dependency
 * injection and other application context management features. The plugin integrates with the Minecraft server, enabling
 * role-playing functionalities with enhanced system monitoring capabilities.</p>
 *
 * <p>The {@code DistrictRoleplay} class is responsible for initializing the plugin during the server startup, managing its
 * operational state, and ensuring a clean shutdown. It also conditionally handles the plugin's behavior when running in a
 * unit test environment by checking the Minecraft version.</p>
 *
 * @author Erik Pförtner
 * @see SpringlifyBukkitPlugin
 * @see Server
 * @since 1.0.0
 */
@SpringlifyApplication(springApplicationClass = DistrictRoleplayApplication.class)
public class DistrictRoleplay extends SpringlifyBukkitPlugin implements Server {

    /**
     * The prefix used for all plugin messages in the chat.
     */
    public static final String PREFIX = "§7District§4-§cRoleplay §8| §7";
    /**
     * The map used to store player flags.
     * <p>
     * This field is used to store player flags that control the behavior of the plugin for individual players.
     * The player flags are used to enable or disable certain functionalities for specific players based on their actions or permissions.
     * </p>
     */
    private static final Map<UUID, Integer> playerFlags = new ConcurrentHashMap<>();
    /**
     * The {@link InteractionHolder} instance used to manage and store all interactions between players and locations.
     * <p>
     * This field is initialized during the plugin's startup and is used to hold all operations from {@link de.district.api.location.interaction.Interactable Interactables}.
     * </p>
     *
     * @see InteractionHolder
     */
    @Getter
    private static InteractionHolder interactionHolder;
    /**
     * The {@link SystemCollector} instance used to gather and access various system metrics and properties.
     * <p>
     * This field is initialized during the plugin's startup and is used to collect system information
     * such as the operating system, JVM details, memory usage, and the number of available processors.
     * The system collector is typically used for monitoring and logging purposes.
     * </p>
     *
     * @see SystemCollector
     */
    private SystemCollector systemCollector;
    /**
     * The {@link Console} instance representing the server console.
     * <p>
     * This field is initialized during the plugin's startup and is used to interact with the server console.
     * The console instance allows the plugin to send messages to the console and access console-related functionalities.
     * </p>
     *
     * @see Console
     */
    private final Console console = new CoreConsole();
    /**
     * The {@link MinecraftVersion} instance representing the version of Minecraft the server is currently running.
     * <p>
     * This field is initialized during the plugin's startup and is used to determine the version of Minecraft
     * the server is running. The Minecraft version is used to provide compatibility with different server versions
     * and to adjust the plugin's behavior accordingly.
     * </p>
     *
     * @see MinecraftVersion
     */
    private MinecraftVersion minecraftVersion;
    private Map<String, Object> libraries = new ConcurrentHashMap<>();

    /**
     * Constructs a new {@code DistrictRoleplay} instance. If the plugin is running
     * in a unit test environment, indicated by the class loader's package name,
     * the Minecraft version is set to {@link MinecraftVersion#UNIT_TEST}.
     */
    public DistrictRoleplay() {
        if (getClassLoader().getClass().getPackageName().startsWith("be.seeseemelk.mockbukkit")) {
            minecraftVersion = MinecraftVersion.UNIT_TEST;
        }
    }

    /**
     * Sets the flags for the specified player.
     *
     * @param player the {@link PluginOfflinePlayer} object for which to set the flags.
     * @param flags  the flags to set for the specified player.
     */
    public static void setPlayerFlags(@NotNull final PluginOfflinePlayer player, final int flags) {
        playerFlags.put(player.getBukkitOfflinePlayer().getUniqueId(), flags);
    }

    /**
     * Called when the plugin is enabled. Initializes the plugin, retrieves the necessary
     * beans from the Spring context, and starts the system metrics collection. If the plugin
     * is running in a unit test environment, it will invoke {@link #onUnitTestEnable()} instead.
     */
    @Override
    public void onEnable() {
        DistrictRoleplayAPI.setServer(this);
        if (minecraftVersion == MinecraftVersion.UNIT_TEST) {
            onUnitTestEnable();
            return;
        }
        getLogger().info("District-Roleplay Systems is starting up...");
        super.onEnable();
        PluginConfiguration pluginConfiguration = getBean(PluginConfiguration.class);
        systemCollector = new CoreSystemCollector().update();
        if (pluginConfiguration != null) {
            getLogger().info("Debug mode is " + (pluginConfiguration.isDebug() ? "enabled" : "disabled"));
            logSystemInformation();
        }
        interactionHolder = new InteractionHolder(new ArrayList<>());
        getLogger().info("District-Roleplay Systems has been started successfully.");
    }

    /**
     * Handles the plugin's initialization when running in a unit test environment.
     * This method sets up the necessary components for testing purposes and logs the startup process.
     */
    private void onUnitTestEnable() {
        getLogger().info("District-Roleplay Systems is starting up in UNIT_TEST mode...");
        systemCollector = new CoreSystemCollector().update();
        interactionHolder = new InteractionHolder(new ArrayList<>());
        getLogger().info("District-Roleplay Systems has been started successfully in UNIT_TEST mode.");
    }

    /**
     * Called when the plugin is disabled. Ensures that all resources are released
     * and logs the shutdown process. If the plugin is running in a unit test environment,
     * it will invoke {@link #onUnitTestDisable()} instead.
     */
    @Override
    public void onDisable() {
        if (minecraftVersion == MinecraftVersion.UNIT_TEST) {
            onUnitTestDisable();
            return;
        }

        getLogger().info("District-Roleplay Systems is shutting down...");
        super.onDisable();
        interactionHolder.clearInteractions();
        getLogger().info("District-Roleplay Systems has been shut down successfully.");
    }

    /**
     * Handles the plugin's shutdown when running in a unit test environment.
     * This method logs the shutdown process for testing purposes.
     */
    private void onUnitTestDisable() {
        getLogger().info("District-Roleplay Systems is shutting down in UNIT_TEST mode...");
        interactionHolder.clearInteractions();
        getLogger().info("District-Roleplay Systems has been shut down successfully in UNIT_TEST mode.");
    }

    /**
     * Returns the {@link Logger} instance used by this plugin for logging purposes.
     *
     * @return a non-null {@link Logger} instance.
     */
    @Override
    public @NotNull Logger getPluginLogger() {
        return getLogger();
    }

    /**
     * Returns the {@link File} representing the plugin's data folder, where
     * all plugin-related data files are stored.
     *
     * @return a non-null {@link File} instance pointing to the plugin's data folder.
     */
    @Override
    public @NotNull File getPluginDataFolder() {
        return getDataFolder();
    }

    /**
     * Returns the {@link SystemCollector} instance used to gather and access
     * various system metrics and properties.
     *
     * @return a non-null {@link SystemCollector} instance.
     */
    @Override
    public @NotNull SystemCollector getSystemCollector() {
        return this.systemCollector;
    }

    /**
     * Returns the {@link MinecraftVersion} instance representing the
     * version of Minecraft the server is currently running.
     *
     * @return a non-null {@link MinecraftVersion} instance.
     */
    @Override
    public @NotNull MinecraftVersion getMinecraftVersion() {
        return this.minecraftVersion;
    }

    /**
     * Logs detailed system information to the plugin's logger. This includes
     * data such as the operating system, JVM details, memory usage, and
     * the number of available processors. This method is typically called
     * when the plugin is in debug mode.
     */
    protected void logSystemInformation() {
        getLogger().log(Level.INFO, "Plugin started on {0} version {1}", new Object[]{
                systemCollector.getOperatingSystem().split(" ")[0],
                systemCollector.getOperatingSystem().split(" ")[1]});
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, "Jvm name: {0}", systemCollector.getJvmName());
        getLogger().log(Level.INFO, "Jvm version: {0}", systemCollector.getJvmVersion());
        getLogger().log(Level.INFO, "Jvm vendor: {0}", systemCollector.getJvmVendor());
        getLogger().log(Level.INFO, "Jvm uptime: {0}", ConvertingUtils.convertMillisecondsToHumanReadable(systemCollector.getJvmUptime()));
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, "Non heap memory (used/max): {0}/{1}", new Object[]{
                ConvertingUtils.convertBytesToHumanReadable(systemCollector.getNonHeapMemoryUsed()),
                ConvertingUtils.convertBytesToHumanReadable(systemCollector.getNonHeapMemoryMax())});
        getLogger().log(Level.INFO, "Heap memory (used/max): {0}/{1}", new Object[]{
                ConvertingUtils.convertBytesToHumanReadable(systemCollector.getHeapMemoryUsed()),
                ConvertingUtils.convertBytesToHumanReadable(systemCollector.getHeapMemoryMax())});
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, "System load average: {0}", systemCollector.getSystemLoadAverage());
        getLogger().log(Level.INFO, "Available processors: {0}", systemCollector.getAvailableProcessors());
        getLogger().log(Level.INFO, " ");
        getLogger().log(Level.INFO, "Minecraft version: {0}", minecraftVersion.getName());
    }

    /**
     * Retrieves a Spring-managed bean from the application context by its type.
     * This method provides access to Spring components and services, which can
     * be used throughout the plugin's operations. If the Spring context is not
     * initialized, this method returns {@code null}.
     *
     * @param <T>   the type of the bean to retrieve.
     * @param clazz the {@code Class} object representing the type of the bean.
     * @return the Spring-managed bean of the specified type, or {@code null} if the context is not initialized.
     */
    @Override
    public @Nullable <T> T getBean(@NotNull final Class<T> clazz) {
        if (getContext() == null) {
            return null;
        }
        return getContext().getBean(clazz);
    }

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
    @Override
    public @Nullable PluginPlayer getPluginPlayer(final @NotNull Player player) {
        return new CorePluginPlayer(player);
    }

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
    @Override
    public @Nullable PluginOfflinePlayer getPluginOfflinePlayer(final @NotNull OfflinePlayer player) {
        return new CorePluginOfflinePlayer(player);
    }

    /**
     * Retrieves the {@link Console} instance representing the server console.
     *
     * <p>This method returns the {@link Console} instance that represents the server console,
     * allowing the plugin to interact with the console and perform console-related operations.</p>
     *
     * @return the {@link Console} instance representing the server console.
     */
    @Override
    public Console getConsole() {
        return this.console;
    }

    /**
     * Registers a plugin command with the specified name and executor.
     *
     * @param plugin the {@link JavaPlugin} instance that owns the command.
     * @param name the name of the command to register.
     * @param executor the {@link PluginCommandExecutor} that handles the command execution.
     */
    @Override
    public void registerPluginCommand(final @NotNull JavaPlugin plugin, final @NotNull String name, final @NotNull PluginCommandExecutor executor) {
        PluginCommand pluginCommand = plugin.getCommand(name);
        if (pluginCommand == null) {
            throw new DistrictRoleplayException(String.format("Failed to register command '%s' for plugin '%s'", name, plugin.getName()));
        }

        pluginCommand.setExecutor(new PluginCommandExecutorWrapper(executor));
    }
}
