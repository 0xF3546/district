package de.district.core.location;

import de.district.api.location.interaction.Interactable;

import java.util.List;

/**
 * The {@code InteractionHolder} class is a container for managing a list of {@link Interactable} objects.
 * It provides methods for adding, removing, and checking interactions within the game environment.
 *
 * <p>This class is implemented as a Java record, which provides a compact syntax for defining immutable data
 * containers. However, in this case, the {@code InteractionHolder} is mutable due to the operations provided
 * on the contained list of interactions.</p>
 *
 * @param interactions the list of {@link Interactable} objects managed by this holder.
 * @author Erik Pförtner
 * @see Interactable
 * @since 1.0.0
 */
public record InteractionHolder(List<Interactable> interactions) {

    /**
     * Adds a new {@link Interactable} to the list of interactions.
     *
     * @param interactable the interactable object to add.
     */
    public void addInteraction(final Interactable interactable) {
        this.interactions.add(interactable);
    }

    /**
     * Removes an {@link Interactable} from the list of interactions.
     *
     * @param interactable the interactable object to remove.
     */
    public void removeInteraction(final Interactable interactable) {
        this.interactions.remove(interactable);
    }

    /**
     * Clears all interactions from the list.
     */
    public void clearInteractions() {
        this.interactions.clear();
    }

    /**
     * Checks if there are any interactions in the list.
     *
     * @return {@code true} if there is at least one interaction in the list, {@code false} otherwise.
     */
    public boolean hasInteractions() {
        return !this.interactions.isEmpty();
    }

    /**
     * Checks if a specific {@link Interactable} is present in the list of interactions.
     *
     * @param interactable the interactable object to check for.
     * @return {@code true} if the interactable is in the list, {@code false} otherwise.
     */
    public boolean hasInteraction(final Interactable interactable) {
        return this.interactions.contains(interactable);
    }
}
