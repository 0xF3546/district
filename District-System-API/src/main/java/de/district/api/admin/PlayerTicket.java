package de.district.api.admin;

import de.district.api.entity.PluginPlayer;
import it.unimi.dsi.fastutil.objects.ObjectList;

import java.time.LocalDateTime;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PlayerTicket {
    PluginPlayer getCreator();
    ObjectList<PluginPlayer> getParticipants();
    void addParticipant(final PluginPlayer player);
    void removeParticipant(final PluginPlayer player);
    String getReason();
    LocalDateTime getCreationDate();
}
