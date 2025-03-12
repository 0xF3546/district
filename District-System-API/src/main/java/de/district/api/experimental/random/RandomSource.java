package de.district.api.experimental.random;

/**
 * <p>
 * The {@code RandomSource} interface defines a contract for generating random numbers across various types,
 * including integers, longs, booleans, floats, doubles, and Gaussian-distributed doubles. It also provides
 * methods for creating new instances, forking instances for parallel computations, and managing seeds.
 * </p>
 *
 * <p>
 * Implementations of this interface may vary in their underlying algorithms, thread-safety, and performance
 * characteristics. The default implementation provided is {@link LegacyRandomSource}, but other implementations
 * like {@link ThreadSafeLegacyRandomSource} are also available for specific use cases.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * RandomSource randomSource = RandomSource.create(12345L);
 * int randomInt = randomSource.nextInt();
 * double randomGaussian = randomSource.nextGaussian();
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 * @see LegacyRandomSource
 * @see ThreadSafeLegacyRandomSource
 * @see PositionalRandomFactory
 */
public interface RandomSource {

    /**
     * The Gaussian spread factor used in Gaussian random number generation.
     *
     * @deprecated This constant is deprecated and may be removed in future versions.
     */
    @Deprecated
    double GAUSSIAN_SPREAD_FACTOR = 2.297;

    /**
     * Creates a new instance of {@code RandomSource} using a uniquely generated seed.
     *
     * <p>
     * This method generates a unique seed and returns a new {@link LegacyRandomSource} instance
     * initialized with that seed.
     * </p>
     *
     * @return a new {@code RandomSource} instance.
     */
    static RandomSource create() {
        return create(RandomSupport.generateUniqueSeed());
    }

    /**
     * Creates a new thread-safe instance of {@code RandomSource} using a uniquely generated seed.
     *
     * <p>
     * This method returns a new {@link ThreadSafeLegacyRandomSource} instance initialized with a unique seed.
     * </p>
     *
     * @return a new thread-safe {@code RandomSource} instance.
     * @deprecated This method is deprecated; consider using non-deprecated alternatives for thread-safe random number generation.
     */
    @Deprecated
    static RandomSource createThreadSafe() {
        return new ThreadSafeLegacyRandomSource(RandomSupport.generateUniqueSeed());
    }

    /**
     * Creates a new instance of {@code RandomSource} using the specified seed.
     *
     * <p>
     * This method returns a new {@link LegacyRandomSource} instance initialized with the given seed.
     * </p>
     *
     * @param seed the seed used to initialize the random source.
     * @return a new {@code RandomSource} instance.
     */
    static RandomSource create(final long seed) {
        return new LegacyRandomSource(seed);
    }

    /**
     * Forks this random source to create a new independent instance.
     *
     * <p>
     * The forked instance will have the same properties as the original but will be seeded independently.
     * </p>
     *
     * @return a new forked {@code RandomSource} instance.
     */
    RandomSource fork();

    /**
     * Forks this random source to create a new {@link PositionalRandomFactory}.
     *
     * <p>
     * The positional random factory allows for the generation of random sources tied to specific coordinates
     * or string hashes.
     * </p>
     *
     * @return a new {@code PositionalRandomFactory} instance.
     */
    PositionalRandomFactory forkPositional();

    /**
     * Sets the seed for this random source.
     *
     * <p>
     * The seed determines the sequence of random numbers that will be generated. Setting the seed
     * allows for reproducible sequences of random numbers.
     * </p>
     *
     * @param seed the new seed value.
     */
    void setSeed(final long seed);

    /**
     * Generates the next random integer.
     *
     * @return the next random integer.
     */
    int nextInt();

    /**
     * Generates the next random integer within the specified bound.
     *
     * <p>
     * The generated integer will be between 0 (inclusive) and the specified bound (exclusive).
     * </p>
     *
     * @param bound the upper bound (exclusive).
     * @return the next random integer within the specified bound.
     */
    int nextInt(final int bound);

    /**
     * Generates the next random integer between two inclusive bounds.
     *
     * <p>
     * The generated integer will be between the specified lower bound (inclusive) and upper bound (inclusive).
     * </p>
     *
     * @param lowerBound the lower bound (inclusive).
     * @param upperBound the upper bound (inclusive).
     * @return the next random integer within the specified bounds.
     */
    default int nextIntBetweenInclusive(final int lowerBound, final int upperBound) {
        return this.nextInt(upperBound - lowerBound + 1) + lowerBound;
    }

    /**
     * Generates the next random long integer.
     *
     * @return the next random long integer.
     */
    long nextLong();

    /**
     * Generates the next random boolean value.
     *
     * @return the next random boolean value.
     */
    boolean nextBoolean();

    /**
     * Generates the next random float value between 0.0 (inclusive) and 1.0 (exclusive).
     *
     * @return the next random float value.
     */
    float nextFloat();

    /**
     * Generates the next random double value between 0.0 (inclusive) and 1.0 (exclusive).
     *
     * @return the next random double value.
     */
    double nextDouble();

    /**
     * Generates the next Gaussian (normally) distributed double value with mean 0.0 and standard deviation 1.0.
     *
     * @return the next Gaussian-distributed double value.
     */
    double nextGaussian();

    /**
     * Generates a triangular-distributed random value with the specified mode and spread.
     *
     * <p>
     * The triangular distribution is useful in cases where values around the mode are more likely
     * than those far from it, providing a simple alternative to Gaussian distributions.
     * </p>
     *
     * @param mode   the mode of the distribution.
     * @param spread the spread of the distribution.
     * @return a triangular-distributed random value.
     */
    default double triangle(final double mode, final double spread) {
        return mode + spread * (this.nextDouble() - this.nextDouble());
    }

    /**
     * Consumes a specified number of random integers without returning them.
     *
     * <p>
     * This method advances the random sequence by consuming the specified number of integers.
     * It can be used to skip over a part of the random sequence.
     * </p>
     *
     * @param count the number of random integers to consume.
     */
    default void consumeCount(final int count) {
        for (int i = 0; i < count; ++i) {
            this.nextInt();
        }
    }

    /**
     * Generates a random integer within the specified bounds (exclusive of the upper bound).
     *
     * <p>
     * The generated integer will be between the specified lower bound (inclusive) and upper bound (exclusive).
     * </p>
     *
     * @param lowerBound the lower bound (inclusive).
     * @param upperBound the upper bound (exclusive).
     * @return the next random integer within the specified bounds.
     * @throws IllegalArgumentException if the lower bound is greater than or equal to the upper bound.
     */
    default int nextInt(final int lowerBound, final int upperBound) {
        if (lowerBound >= upperBound) {
            throw new IllegalArgumentException("bound - origin is non positive");
        } else {
            return lowerBound + this.nextInt(upperBound - lowerBound);
        }
    }
}
