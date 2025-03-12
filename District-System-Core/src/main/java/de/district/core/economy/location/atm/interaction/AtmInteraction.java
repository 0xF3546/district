package de.district.core.economy.location.atm.interaction;

import de.district.api.DistrictRoleplayAPI;
import de.district.api.economy.GameBank;
import de.district.api.entity.PluginPlayer;
import de.district.api.inventorymanager.CustomItem;
import de.district.api.inventorymanager.InventoryManager;
import de.district.api.location.Location;
import de.district.api.location.LocationType;
import de.district.api.location.interaction.Interactable;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

import static de.splatgames.springlify.platform.item.ItemBuilder.ItemStackItemBuilder;

/**
 * Represents an interaction that occurs when a player interacts with an ATM location.
 *
 * <p>This class extends the {@link Interactable} class, providing a specific interaction
 * that occurs when a player interacts with an ATM location. The interaction typically
 * involves the player performing some action at the specified location.</p>
 *
 * <p>Subclasses of this class must implement the {@link #interact(Player, Location)} method
 * to define the specific interaction logic that occurs when a player interacts with an ATM.</p>
 *
 * @author Erik Pförtner
 * @see Interactable
 * @since 1.0.0
 */
public class AtmInteraction extends Interactable {
    /**
     * Constructs a new {@code AtmInteraction} associated with a specific {@link LocationType}.
     */
    protected AtmInteraction() {
        super(LocationType.ATM);
    }

    /**
     * Defines the interaction that occurs when a player interacts with a specific location.
     *
     * <p>This method must be implemented by subclasses to define the specific interaction logic.
     * The interaction typically involves the player performing some action at the specified location.</p>
     *
     * @param player   the player interacting with the location, must not be {@code null}.
     * @param location the location being interacted with, must not be {@code null}.
     */
    @Override
    public void interact(final @NotNull Player player, final @NotNull Location location) {
        PluginPlayer pluginPlayer = DistrictRoleplayAPI.getPluginPlayer(player);
        if (pluginPlayer == null) {
            throw new IllegalStateException(String.format("Could not find plugin player for player %s, this is not possible in normal conditions.", player.getName()));
        }

        boolean hasBankProvider = pluginPlayer.hasBankProvider();
        if (hasBankProvider) {
            // handleExistingBankProvider(player);
        } else {
            handleNonExistingBankProvider(pluginPlayer);
        }
    }

    private void handleNonExistingBankProvider(final @NotNull PluginPlayer player) {
        InventoryManager inventory = new InventoryManager(player.getBukkitPlayer(), 27, Component.text("§dATM §8» §cKein Bankanbieter"), true);
        Inventory bukkitInventory = inventory.getInventory();

        bukkitInventory.setItem(0, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());
        bukkitInventory.setItem(8, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());
        bukkitInventory.setItem(9, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());
        bukkitInventory.setItem(17, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());
        bukkitInventory.setItem(18, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());
        bukkitInventory.setItem(26, ItemStackItemBuilder.fromMaterial(Material.RED_STAINED_GLASS).build());

        inventory.setItem(new CustomItem(11, ItemStackItemBuilder.
                fromMaterial(Material.PLAYER_HEAD)
                .skullBuilder().owner(player.getBukkitPlayer())
                .apply()
                .setName("§8» §6" + player.getBukkitPlayer().getName())
                .appendLore(
                        "§7Anbieter: §cKein Bankanbieter",
                        "§7Kontostand: §c0.00€")
                .build()
        ) {
            @Override
            public void onClick(final InventoryClickEvent event) {
                player.sendMessage(Component.text("§cDu hast noch keinen Bankanbieter ausgewählt."));
                player.sendMessage(Component.text("§7Gehe zu einer Bank in deiner Nähe, um einen Bankanbieter auszuwählen."));
                player.getBukkitPlayer().closeInventory();
            }
        });

        bukkitInventory.setItem(13, ItemStackItemBuilder
                .fromMaterial(Material.BARRIER)
                .setName("§8» §cKein Zugriff")
                .appendLore("§7Du hast keinen Bankkonto eröffnet.")
                .build());

        inventory.setItem(new CustomItem(15, ItemStackItemBuilder
                .fromMaterial(Material.MAP)
                .setName("§8» §6Nächste Bank finden")
                .appendLore("§7Klicke hier, um die nächste Bank in deiner Nähe zu finden.")
                .build()) {
            @Override
            public void onClick(final InventoryClickEvent event) {
                List<GameBank> banks = player.findNearbyBanks(250);
                if (banks.isEmpty()) {
                    Optional<GameBank> nearestBank = player.findNearestBank();
                    if (nearestBank.isPresent()) {
                        player.sendMessage(Component.text("§7Die nächste Bank ist §6" + nearestBank.get().getLocation().getName() + "§7."));
                        player.sendMessage(Component.text(String.format(
                                "§7Gehe zu §6%s X: %s Y: %s Z: %s§7, um ein Bankkonto zu eröffnen.",
                                nearestBank.get().getLocation().getName(),
                                (int) nearestBank.get().getLocation().getX(),
                                (int) nearestBank.get().getLocation().getY(),
                                (int) nearestBank.get().getLocation().getZ()
                        )));
                    } else {
                        player.sendMessage(Component.text("§cEs konnte keine Bank in deiner Nähe gefunden werden."));
                        player.sendMessage(Component.text("§7Dies passiert meist, wenn die Administratoren keine Banken erstellt haben."));
                    }
                }
            }
        });
    }
}
