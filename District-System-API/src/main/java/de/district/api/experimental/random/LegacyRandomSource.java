package de.district.api.experimental.random;

import com.google.common.annotations.VisibleForTesting;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * The {@code LegacyRandomSource} class provides an implementation of the {@link BitRandomSource}
 * interface using a legacy algorithm for random number generation. This class is thread-safe
 * and suitable for applications requiring reproducible and consistent random sequences.
 * </p>
 *
 * <p>
 * <b>Key Features:</b>
 * <ul>
 *     <li>Thread-safe random number generation using {@link AtomicLong}.</li>
 *     <li>Support for generating random integers, longs, and Gaussian-distributed doubles.</li>
 *     <li>Ability to fork new random sources and positional random factories for distributed randomness.</li>
 * </ul>
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * LegacyRandomSource randomSource = new LegacyRandomSource(12345L);
 * int randomInt = randomSource.nextInt();
 * long randomLong = randomSource.nextLong();
 * double randomGaussian = randomSource.nextGaussian();
 * }</pre>
 * </p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 * @see BitRandomSource
 * @see RandomSource
 * @see MarsagliaPolarGaussian
 */
public class LegacyRandomSource implements BitRandomSource {

    /**
     * The internal seed used for random number generation.
     */
    private final AtomicLong seed = new AtomicLong();

    /**
     * The Gaussian random number generator using the Marsaglia polar method.
     */
    private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

    /**
     * Constructs a new {@code LegacyRandomSource} with the specified initial seed.
     *
     * @param seed the initial seed value.
     */
    public LegacyRandomSource(final long seed) {
        this.setSeed(seed);
    }

    /**
     * Creates a new independent {@link RandomSource} by using the next generated long value as the seed.
     *
     * @return a new {@code LegacyRandomSource} instance.
     */
    @Override
    public RandomSource fork() {
        return new LegacyRandomSource(this.nextLong());
    }

    /**
     * Creates a new {@link PositionalRandomFactory} using the next generated long value as the seed.
     *
     * @return a new {@code LegacyPositionalRandomFactory} instance.
     */
    @Override
    public PositionalRandomFactory forkPositional() {
        return new LegacyPositionalRandomFactory(this.nextLong());
    }

    /**
     * Sets the seed for this random number generator.
     *
     * <p>
     * The seed is modified using a bitwise XOR operation with a predefined constant
     * to ensure a wide distribution of initial states.
     * </p>
     *
     * @param seed the new seed value.
     * @throws RuntimeException if multiple threads attempt to set the seed simultaneously.
     */
    @Override
    public void setSeed(final long seed) {
        long modifiedSeed = (seed ^ 0x5DEECE66DL) & ((1L << 48) - 1);
        if (!this.seed.compareAndSet(this.seed.get(), modifiedSeed)) {
            throw new RuntimeException(ThreadingDetector.makeThreadingException("LegacyRandomSource", null));
        }
        this.gaussianSource.reset();
    }

    /**
     * Generates the next random number with the specified number of bits.
     *
     * @param bits the number of random bits to generate (between 1 and 32).
     * @return the next random number.
     * @throws RuntimeException if multiple threads attempt to generate numbers simultaneously.
     */
    @Override
    public int next(final int bits) {
        long currentSeed;
        long nextSeed;
        do {
            currentSeed = this.seed.get();
            nextSeed = (currentSeed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
        } while (!this.seed.compareAndSet(currentSeed, nextSeed));

        return (int) (nextSeed >>> (48 - bits));
    }

    /**
     * Generates the next random Gaussian (normally) distributed double value with mean 0.0 and standard deviation 1.0.
     *
     * <p>
     * This method utilizes the {@link MarsagliaPolarGaussian} algorithm for efficient and high-quality
     * Gaussian random number generation.
     * </p>
     *
     * @return the next random Gaussian-distributed double value.
     */
    @Override
    public double nextGaussian() {
        return this.gaussianSource.nextGaussian();
    }

    /**
     * <p>
     * The {@code LegacyPositionalRandomFactory} class provides a factory for creating positional random sources
     * based on specific coordinates or string hashes. This is particularly useful for generating consistent
     * random values tied to specific positions, such as in procedural world generation.
     * </p>
     *
     * <p>
     * <b>Usage Example:</b>
     * <pre>{@code
     * LegacyPositionalRandomFactory factory = new LegacyPositionalRandomFactory(12345L);
     * RandomSource positionRandom = factory.at(10, 20, 30);
     * }</pre>
     * </p>
     *
     * @see PositionalRandomFactory
     * @see RandomSource
     */
    public static class LegacyPositionalRandomFactory implements PositionalRandomFactory {

        /**
         * The base seed used for generating positional random sources.
         */
        private final long seed;

        /**
         * Constructs a new {@code LegacyPositionalRandomFactory} with the specified seed.
         *
         * @param seed the base seed value.
         */
        public LegacyPositionalRandomFactory(final long seed) {
            this.seed = seed;
        }

        /**
         * Creates a new {@link RandomSource} based on the specified 3D coordinates.
         *
         * <p>
         * The generated random source will produce consistent random values for the given position.
         * </p>
         *
         * @param x the X-coordinate.
         * @param y the Y-coordinate.
         * @param z the Z-coordinate.
         * @return a new {@code LegacyRandomSource} instance tied to the specified position.
         */
        @Override
        public RandomSource at(final int x, final int y, final int z) {
            long positionSeed = Mth.getSeed(x, y, z);
            long combinedSeed = positionSeed ^ this.seed;
            return new LegacyRandomSource(combinedSeed);
        }

        /**
         * Creates a new {@link RandomSource} based on the hash code of the specified string.
         *
         * <p>
         * This method is useful for generating consistent random sources based on textual identifiers.
         * </p>
         *
         * @param seedString the string to hash for seed generation.
         * @return a new {@code LegacyRandomSource} instance tied to the hash of the specified string.
         */
        @Override
        public RandomSource fromHashOf(final String seedString) {
            long stringSeed = seedString.hashCode() ^ this.seed;
            return new LegacyRandomSource(stringSeed);
        }

        /**
         * Appends a string representation of this factory's configuration to the provided {@link StringBuilder}.
         *
         * <p>
         * This method is primarily intended for debugging and testing purposes.
         * </p>
         *
         * @param stringBuilder the {@code StringBuilder} to append to.
         */
        @VisibleForTesting
        @Override
        public void parityConfigString(final StringBuilder stringBuilder) {
            stringBuilder.append("LegacyPositionalRandomFactory{seed=").append(this.seed).append("}");
        }
    }
}
