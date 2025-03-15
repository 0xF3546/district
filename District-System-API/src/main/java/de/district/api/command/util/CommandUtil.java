package de.district.api.command.util;

import de.district.api.command.annotation.FactionRequired;
import de.district.api.entity.PluginPlayer;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class CommandUtil {
    public static boolean inFaction(@NotNull final PluginPlayer pluginPlayer, @NotNull final FactionRequired factionRequired) {
        String[] factions = factionRequired.factions();
        for (String faction : factions) {
            // TODO: Implement faction system
            /*if (pluginPlayer.getFaction().equalsIgnoreCase(faction)) {
                return true;
            }*/
        }

        return false;
    }

    public static boolean minLevel(@NotNull final PluginPlayer pluginPlayer, final int minLevel) {
        // TODO: Implement faction system
        // return pluginPlayer.getFactionLevel() >= minLevel;
        return false;
    }

    public static boolean isLeader(@NotNull final PluginPlayer pluginPlayer) {
        // TODO: Implement faction system
        // return pluginPlayer.isLeader();
        return false;
    }
}
