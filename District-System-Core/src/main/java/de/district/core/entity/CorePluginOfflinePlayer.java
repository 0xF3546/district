package de.district.core.entity;

import de.district.api.DistrictAPI;
import de.district.api.economy.BalanceAccessor;
import de.district.api.economy.BankType;
import de.district.api.entity.PluginOfflinePlayer;
import de.district.core.DistrictRoleplay;
import de.splatgames.aether.permissions.api.PermissionManager;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

/**
 * The {@code CorePluginOfflinePlayer} class is an implementation of the {@link PluginOfflinePlayer} interface,
 * providing access to offline player data within the District Roleplay System (DRS). This class manages player-related
 * data such as balance access, flags, and bank provider information.
 *
 * <p>It interacts with the {@link DistrictRoleplay} system to push and pull player flags and handles the creation
 * and management of bank accounts associated with the player.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public class CorePluginOfflinePlayer implements PluginOfflinePlayer {
    private final OfflinePlayer offlinePlayer;
    private BalanceAccessor balanceAccessor;

    /**
     * Constructs a new {@code CorePluginOfflinePlayer} instance for the specified {@link OfflinePlayer}.
     *
     * @param offlinePlayer the Bukkit {@code OfflinePlayer} associated with this instance.
     */
    public CorePluginOfflinePlayer(@NotNull final OfflinePlayer offlinePlayer) {
        this.offlinePlayer = offlinePlayer;
    }

    /**
     * Retrieves the {@link OfflinePlayer} associated with this instance.
     *
     * @return the associated {@code OfflinePlayer}.
     */
    @Override
    public @NotNull OfflinePlayer getBukkitOfflinePlayer() {
        return this.offlinePlayer;
    }

    /**
     * Opens a new bank account for the player. This method is currently unimplemented.
     *
     * @param provider      the bank provider.
     * @param type          the type of bank account.
     * @param hasOldAccount whether the player has an old account.
     */
    @Override
    public void openNewBankAccount(final @NotNull String provider, final BankType type, final boolean hasOldAccount) {
        // Method not yet implemented
    }

    /**
     * Checks if the player has a bank provider. This method is currently unimplemented.
     *
     * @return {@code false} as this method is not yet implemented.
     */
    @Override
    public boolean hasBankProvider() {
        return false;
    }

    /**
     * Retrieves the bank provider associated with the player. This method is currently unimplemented.
     *
     * @return an empty string as this method is not yet implemented.
     */
    @Override
    public @Nullable String getBankProvider() {
        return "";
    }

    /**
     * Sets the bank provider for the player. This method is currently unimplemented.
     *
     * @param provider the bank provider to set.
     */
    @Override
    public void setBankProvider(final @NotNull String provider) {
        // Method not yet implemented
    }

    /**
     * Retrieves the {@link BalanceAccessor} for this player, if available.
     *
     * @return the {@code BalanceAccessor} associated with this player, or {@code null} if not available.
     */
    @Override
    public @Nullable BalanceAccessor balanceAccessor() {
        return balanceAccessor;
    }

    @Override
    public @NotNull UUID getUniqueId() {
        return offlinePlayer.getUniqueId();
    }

    @Override
    public @NotNull PermissionManager getPermissionManager() {
        return DistrictAPI.getPermissionManager();
    }
}
