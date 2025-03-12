package de.district.api.experimental.random;

/**
 * <p>
 * The {@code SingleThreadedRandomSource} class provides a random number generator implementation that is
 * designed for single-threaded use. This class implements the {@link BitRandomSource} interface, allowing
 * it to generate random numbers with various bit lengths, as well as Gaussian-distributed values.
 * </p>
 *
 * <p>
 * This implementation is not thread-safe and should only be used in single-threaded contexts. For
 * multi-threaded applications, consider using a thread-safe alternative like {@code ThreadSafeLegacyRandomSource}.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * SingleThreadedRandomSource randomSource = new SingleThreadedRandomSource(12345L);
 * int randomInt = randomSource.nextInt();
 * double randomGaussian = randomSource.nextGaussian();
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @see BitRandomSource
 * @see MarsagliaPolarGaussian
 * @since 1.0.0
 */
public class SingleThreadedRandomSource implements BitRandomSource {

    /**
     * Gaussian random number generator using the Marsaglia polar method.
     */
    private final MarsagliaPolarGaussian gaussianSource = new MarsagliaPolarGaussian(this);

    /**
     * The internal seed used for random number generation.
     */
    private long seed;

    /**
     * Constructs a new {@code SingleThreadedRandomSource} with the specified initial seed.
     *
     * @param seed the initial seed value.
     */
    public SingleThreadedRandomSource(final long seed) {
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
     * @return a new {@code SingleThreadedRandomSource} instance.
     */
    @Override
    public RandomSource fork() {
        return new SingleThreadedRandomSource(this.nextLong());
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
     * The seed is modified using a bitwise XOR operation with a predefined constant
     * to ensure a wide distribution of initial states.
     * </p>
     *
     * @param seed the new seed value.
     */
    @Override
    public void setSeed(final long seed) {
        this.seed = (seed ^ 25214903917L) & 281474976710655L;
        this.gaussianSource.reset();
    }

    /**
     * Generates the next random number with the specified number of bits.
     *
     * <p>
     * The generated number uses the internal seed and is updated with each call.
     * </p>
     *
     * @param bits the number of random bits to generate (between 1 and 32).
     * @return the next random number.
     */
    @Override
    public int next(final int bits) {
        long newSeed = this.seed * 25214903917L + 11L & 281474976710655L;
        this.seed = newSeed;
        return (int) (newSeed >> (48 - bits));
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
