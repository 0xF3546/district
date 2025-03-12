package de.district.core.user.service;

import de.district.core.user.domain.User;
import de.district.core.user.domain.dto.UserDto;
import de.district.core.user.repository.UserRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The {@code UserServiceListener} class is a Spring service that listens for player-related events in the game,
 * specifically handling the first time a player joins the server.
 * If a player is joining for the first time, this
 * service will create a new {@link User} entity and save it to the database.
 *
 * <p>This service is annotated with {@link Service} to indicate that it is a Spring-managed component and implements
 * the {@link Listener} interface to handle Bukkit events.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Service
public class UserServiceListener implements Listener {

    @Autowired
    private UserRepository userRepository;

    /**
     * Handles the {@link PlayerJoinEvent} and checks if the player is joining the server for the first time.
     * If so, a new {@link User} entity is created and saved to the database.
     *
     * @param event the event that is triggered when a player joins the server.
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerFirstJoin(final PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (userRepository.findByUuid(uuid).isEmpty()) {
            // TODO: Implement region detection
            User user = new User(new UserDto(uuid, System.currentTimeMillis(), "germany", false));
            userRepository.save(user);
        }
    }
}
