package de.district.core.location.service;

import de.district.api.location.Location;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code LocationListener} class is responsible for handling player interactions with locations in the game world.
 * It listens for {@link PlayerInteractEvent} events and processes interactions when a player interacts with specific locations.
 *
 * <p>This class is annotated with {@link Service}, making it a Spring-managed component that can be injected
 * and used in the application. It interacts with the {@link CoreLocationService} to manage location-based interactions.</p>
 *
 * @author Erik Pförtner
 * @see PlayerInteractEvent
 * @see CoreLocationService
 * @since 1.0.0
 */
@Service
public class LocationListener implements Listener {

    @Autowired
    private CoreLocationService locationService;

    /**
     * Handles the {@link PlayerInteractEvent} event, which is triggered when a player interacts with an object in the game world.
     * This method checks if the player is near any registered locations and processes the interaction if applicable.
     *
     * @param event the player interaction event.
     */
    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        org.bukkit.Location playerLocation = player.getLocation();

        List<Location> locations = locationService.getAllLocations();

        Action action = event.getAction();

        for (Location location : locations) {
            if (isNearby(playerLocation, location)) {
                handleLocationInteraction(player, location, action);
            }
        }
    }

    /**
     * Checks if the player is within a certain distance (e.g., 5 blocks) of a given location.
     * This method is used as an anti-cheat measure to prevent range/reach cheats.
     *
     * @param playerLocation the player's current location.
     * @param location       the location to check proximity against.
     * @return {@code true} if the player is within 5 blocks of the location, {@code false} otherwise.
     */
    private boolean isNearby(@NotNull final org.bukkit.Location playerLocation, @NotNull final Location location) {
        return playerLocation.distance(new org.bukkit.Location(
                Bukkit.getWorld(location.getWorld()),
                location.getX(),
                location.getY(),
                location.getZ())) < 5;
    }

    /**
     * Handles the interaction between a player and a location when the player performs a right-click action.
     * This method delegates the interaction logic to the {@link CoreLocationService}.
     *
     * @param player   the player interacting with the location.
     * @param location the location being interacted with.
     * @param action   the action performed by the player, expected to be {@link Action#RIGHT_CLICK_BLOCK}.
     */
    private void handleLocationInteraction(@NotNull final Player player, @NotNull final Location location, @NotNull final Action action) {
        if (action == Action.RIGHT_CLICK_BLOCK) {
            locationService.interactWithLocation(player, location);
        }
    }
}
