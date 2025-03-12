package de.district.api.experimental.random;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * The {@code ThreadSafeLegacyRandomSource} class provides a thread-safe implementation of the {@link BitRandomSource}
 * interface, utilizing an atomic long for seed management. This class is designed to be used in multi-threaded
 * environments where thread safety is a concern. However, it is marked as deprecated due to the availability of more
 * modern and efficient alternatives.
 * </p>
 *
 * <p>
 * This class also supports generating Gaussian-distributed random numbers using the Marsaglia polar method and can
 * create new independent instances or positional random factories.
 * </p>
 *
 * <p>
 * <b>Note:</b> This class is deprecated and should not be used in new projects. Consider using non-deprecated
 * alternatives for thread-safe random number generation.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * ThreadSafeLegacyRandomSource randomSource = new ThreadSafeLegacyRandomSource(12345L);
 * int randomInt = randomSource.nextInt();
 * double randomGaussian = randomSource.nextGaussian();
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @see BitRandomSource
 * @see MarsagliaPolarGaussian
 * @since 1.0.0
 * @deprecated This class is deprecated and may be removed in future versions. Use modern alternatives for
 * thread-safe random number generation.
 */
@Deprecated
public class ThreadSafeLegacyRandomSource implements BitRandomSource {

    /**
     * The internal seed used for random number generation, managed atomically for thread safety.
     */
    private final AtomicLong seed = new AtomicLong();

    /**
     * Gaussian random number generator using the Marsaglia polar method.
     */
    private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

    /**
     * Constructs a new {@code ThreadSafeLegacyRandomSource} with the specified initial seed.
     *
     * @param seed the initial seed value.
     */
    public ThreadSafeLegacyRandomSource(final long seed) {
        this.setSeed(seed);
    }

    /**
     * Creates a new independent {@link RandomSource} by using the next generated long value as the seed.
     *
     * <p>
     * The forked {@code RandomSource} is independent of the original, meaning that it generates its own
     * sequence of random values starting from the new seed.
     * </p>
     *
     * @return a new {@code ThreadSafeLegacyRandomSource} instance.
     */
    @Override
    public RandomSource fork() {
        return new ThreadSafeLegacyRandomSource(this.nextLong());
    }

    /**
     * Creates a new {@link PositionalRandomFactory} using the next generated long value as the seed.
     *
     * <p>
     * This factory can be used to generate random sources tied to specific positions or string hashes.
     * </p>
     *
     * @return a new {@code LegacyPositionalRandomFactory} instance.
     */
    @Override
    public PositionalRandomFactory forkPositional() {
        return new LegacyRandomSource.LegacyPositionalRandomFactory(this.nextLong());
    }

    /**
     * Sets the seed for this random number generator.
     *
     * <p>
     * The seed is managed atomically to ensure thread safety and is modified using a bitwise XOR operation
     * with a predefined constant to ensure a wide distribution of initial states.
     * </p>
     *
     * @param seed the new seed value.
     */
    @Override
    public void setSeed(final long seed) {
        this.seed.set((seed ^ 25214903917L) & 281474976710655L);
    }

    /**
     * Generates the next random number with the specified number of bits.
     *
     * <p>
     * The generated number uses the internal atomic seed and is updated with each call, ensuring thread safety.
     * </p>
     *
     * @param bits the number of random bits to generate (between 1 and 32).
     * @return the next random number.
     */
    @Override
    public int next(final int bits) {
        long currentSeed, newSeed;
        do {
            currentSeed = this.seed.get();
            newSeed = currentSeed * 25214903917L + 11L & 281474976710655L;
        } while (!this.seed.compareAndSet(currentSeed, newSeed));

        return (int) (newSeed >>> (48 - bits));
    }

    /**
     * Generates the next Gaussian (normally) distributed double value with mean 0.0 and standard deviation 1.0.
     *
     * <p>
     * This method uses the Marsaglia polar method to generate the Gaussian value.
     * </p>
     *
     * @return the next Gaussian-distributed double value.
     */
    @Override
    public double nextGaussian() {
        return this.gaussianSource.nextGaussian();
    }
}
