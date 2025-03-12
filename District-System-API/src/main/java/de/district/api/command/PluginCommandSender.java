package de.district.api.command;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;

public interface PluginCommandSender {

    /**
     * Sends a message to the sender.
     *
     * @param message the message to send, never {@code null}.
     * @deprecated Use {@link #sendMessage(Component)} or {@link #sendMessage(ComponentLike)} instead.
     */
    @Deprecated
    void sendMessage(final @NotNull String message);

    /**
     * Sends a message to the sender, with an optional prefix.
     * This method is implemented in the core implementation and should be overridden for custom implementations.
     * (or not because it's deprecated)
     *
     * @param message the message to send, never {@code null}.
     * @param prefix  whether to include a prefix in the message.
     * @throws UnsupportedOperationException Implemented in the core implementation. For custom implementations, this method should be overridden.
     * @deprecated This method is not implemented and will throw an exception.
     */
    @Deprecated
    default void sendMessage(final @NotNull String message, final boolean prefix) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Sends a message to the sender as a {@link Component}, with a default prefix.
     *
     * @param message the message to send, never {@code null}.
     */
    default void sendMessage(@NotNull final Component message) {
        sendMessage(message, true);
    }

    /**
     * Sends a message to the sender as a {@link Component}, with an optional prefix.
     * This method is implemented in the core implementation and should be overridden for custom implementations.
     *
     * @param message the message to send, never {@code null}.
     * @param prefix  whether to include a prefix in the message.
     * @throws UnsupportedOperationException Implemented in the core implementation. For custom implementations, this method should be overridden.
     */
    default void sendMessage(@NotNull final Component message, final boolean prefix) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    /**
     * Sends a message to the sender as a {@link ComponentLike}, converting it to a {@link Component}.
     *
     * @param message the message to send, never {@code null}.
     */
    default void sendMessage(@NotNull final ComponentLike message) {
        this.sendMessage(message.asComponent());
    }
}
