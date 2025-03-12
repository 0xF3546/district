package de.district.api.experimental.random;

import com.google.common.annotations.VisibleForTesting;

/**
 * <p>
 * The {@code PositionalRandomFactory} interface defines a contract for creating {@link RandomSource}
 * instances that are tied to specific positions or derived from string hashes. This is particularly useful
 * in applications like procedural generation where reproducible randomness is required for specific coordinates
 * or identifiers.
 * </p>
 *
 * <p>
 * Implementations of this interface provide methods for generating random sources based on string hashes
 * and 3D coordinates, ensuring that the same inputs will always yield the same random output.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * PositionalRandomFactory factory = ...; // Implementation of PositionalRandomFactory
 * RandomSource randomSource = factory.at(10, 20, 30);
 * RandomSource hashedSource = factory.fromHashOf("exampleString");
 * }</pre>
 * </p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 * @see RandomSource
 */
public interface PositionalRandomFactory {

    /**
     * Creates a {@link RandomSource} instance derived from the hash of the specified string.
     *
     * <p>
     * This method generates a consistent random source for a given string input. It is useful in scenarios
     * where a specific string identifier needs to produce a reproducible sequence of random numbers.
     * </p>
     *
     * @param inputString the string from which the random source will be derived.
     * @return a {@code RandomSource} instance generated from the hash of the input string.
     */
    RandomSource fromHashOf(final String inputString);

    /**
     * Creates a {@link RandomSource} instance tied to the specified 3D coordinates.
     *
     * <p>
     * This method generates a consistent random source for a given set of coordinates. It is commonly used
     * in procedural generation contexts where the random output needs to be tied to specific spatial locations.
     * </p>
     *
     * @param x the X-coordinate.
     * @param y the Y-coordinate.
     * @param z the Z-coordinate.
     * @return a {@code RandomSource} instance tied to the specified coordinates.
     */
    RandomSource at(final int x, final int y, final int z);

    /**
     * Appends a string representation of this factory's configuration to the provided {@link StringBuilder}.
     *
     * <p>
     * This method is primarily intended for testing and debugging purposes. It allows developers to capture
     * and verify the configuration of the factory, ensuring that it behaves as expected in different scenarios.
     * </p>
     *
     * <p>
     * <b>Note:</b> This method is annotated with {@link VisibleForTesting}, indicating that it is primarily
     * intended for use in test environments and may not be relevant in production code.
     * </p>
     *
     * @param stringBuilder the {@code StringBuilder} to append the configuration string to.
     */
    @VisibleForTesting
    void parityConfigString(final StringBuilder stringBuilder);
}
