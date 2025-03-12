package de.district.core.admin.service;

import de.district.api.entity.PluginPlayer;
import org.bukkit.Color;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.springframework.stereotype.Service;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class AdminService {
    public void sendMessage(final String message, final Color color) {

    }

    public void togglePlayerAduty(final PluginPlayer player) {
        player.setAduty(!player.isAduty());
        sendMessage(player.getBukkitPlayer().getName() + " ist nun " + (player.isAduty() ? "im" : "nicht mehr im") + " Aduty.", Color.GREEN);
        if (player.isAduty()) {
            player.getBukkitPlayer().addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, Integer.MAX_VALUE, 0, true, false));
        } else {
            player.getBukkitPlayer().removePotionEffect(PotionEffectType.GLOWING);
        }
    }
}
