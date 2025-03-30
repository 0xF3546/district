package de.district.core.admin;

import de.district.api.DistrictAPI;
import de.district.api.admin.PlayerTicket;
import de.district.api.entity.PluginPlayer;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import jakarta.persistence.*;
import org.bukkit.Bukkit;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Entity
@Table(name = "tickets")
public class Ticket implements PlayerTicket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "creator", nullable = false)
    private final UUID creator;

    @ElementCollection
    @CollectionTable(name = "participants", joinColumns = @JoinColumn(name = "ticket_id"))
    private final ObjectList<UUID> participants;

    @Column(name = "reason", nullable = false)
    private final String reason;

    @Column(name = "created", nullable = false)
    private final LocalDateTime creationDate;
    @Override
    public PluginPlayer getCreator() {
        if (Bukkit.getPlayer(creator) != null) {
            return DistrictAPI.getPluginPlayer(Bukkit.getPlayer(creator));
        }
        return null;
    }

    @Override
    public ObjectList<PluginPlayer> getParticipants() {
        ObjectList<PluginPlayer> participants = new ObjectArrayList<>();
        for (UUID uuid : this.participants) {
            if (Bukkit.getPlayer(uuid) != null) {
                participants.add(DistrictAPI.getPluginPlayer(Bukkit.getPlayer(uuid)));
            }
        }
        return participants;
    }

    @Override
    public void addParticipant(PluginPlayer player) {
        participants.add(player.getUUID());
    }

    @Override
    public void removeParticipant(PluginPlayer player) {
        participants.remove(player.getUUID());
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Ticket(final PluginPlayer creator, final String reason, final LocalDateTime creationDate) {
        this.creator = creator.getUUID();
        this.reason = reason;
        this.creationDate = creationDate;
        this.participants = new ObjectArrayList<>();
    }
}
