package de.district.core.admin.service;

import de.district.api.admin.PlayerTicket;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import org.springframework.stereotype.Service;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class TicketService {
    private final ObjectList<PlayerTicket> tickets = new ObjectArrayList<>();

    public void addTicket(final PlayerTicket ticket) {
        this.tickets.add(ticket);
    }

    public void removeTicket(final PlayerTicket ticket) {
        this.tickets.remove(ticket);
    }

    public ObjectList<PlayerTicket> getTickets() {
        return this.tickets;
    }

    public PlayerTicket getTicketByCreator(final String creator) {
        return this.tickets.stream().filter(ticket -> ticket.getCreator().getName().equals(creator)).findFirst().orElse(null);
    }

    public PlayerTicket getByPlayer(final String player) {
        return this.tickets.stream().filter(ticket -> ticket.getCreator().getName().equals(player) || ticket.getParticipants().stream().anyMatch(participant -> participant.getName().equals(player))).findFirst().orElse(null);
    }
}
