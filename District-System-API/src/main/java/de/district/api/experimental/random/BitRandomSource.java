package de.district.api.experimental.random;

/**
 * <p>
 * The {@code BitRandomSource} interface provides a contract for generating random numbers
 * using a bit-based random source. Implementations of this interface are expected to
 * deliver high-quality random number generation with methods for various data types,
 * including integers, long integers, booleans, floats, and doubles.
 * </p>
 *
 * <p>
 * This interface extends the {@link RandomSource} and offers additional functionalities
 * specifically for bit manipulation and random number generation with different ranges and precisions.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>
 * {@code
 * BitRandomSource randomSource = ...; // Implementation of BitRandomSource
 * int randomInt = randomSource.nextInt();
 * boolean randomBool = randomSource.nextBoolean();
 * }
 * </pre>
 * </p>
 *
 * <p>
 * This interface is particularly useful in scenarios where deterministic and high-performance
 * random number generation is critical, such as in simulations, games, and cryptographic applications.
 * </p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 * @see RandomSource
 */
public interface BitRandomSource extends RandomSource {

    /**
     * Generates the next random number with the specified number of bits.
     *
     * <p>
     * This method is designed to be used by other methods to generate random numbers
     * with a specific bit length, providing flexibility in how random values are generated
     * for different data types.
     * </p>
     *
     * @param numBits the number of bits of the random number to generate. Must be between 1 and 32.
     * @return the next random number within the specified bit length.
     */
    int next(final int numBits);

    /**
     * Generates the next random integer using 32 bits.
     *
     * <p>
     * This method provides a straightforward way to obtain a full 32-bit random integer.
     * It is useful when a random integer with the maximum range of an integer is required.
     * </p>
     *
     * @return the next random integer.
     */
    default int nextInt() {
        return this.next(32);
    }

    /**
     * Generates the next random integer within a specified bound.
     *
     * <p>
     * The generated integer will be between 0 (inclusive) and the specified {@code bound} (exclusive).
     * If the {@code bound} is a power of two, this method uses optimized bit manipulation.
     * Otherwise, it uses modulo reduction to ensure uniform distribution of results.
     * </p>
     *
     * @param bound the upper bound (exclusive) on the random number to be returned. Must be positive.
     * @return the next random integer within the specified bound.
     * @throws IllegalArgumentException if {@code bound} is non-positive.
     */
    default int nextInt(final int bound) {
        if (bound <= 0) {
            throw new IllegalArgumentException("Bound must be positive");
        } else if ((bound & (bound - 1)) == 0) {
            return (int) ((long) bound * (long) this.next(31) >> 31);
        } else {
            int randomBits;
            int result;
            do {
                randomBits = this.next(31);
                result = randomBits % bound;
            } while (randomBits - result + (bound - 1) < 0);

            return result;
        }
    }

    /**
     * Generates the next random long integer using 64 bits.
     *
     * <p>
     * This method combines two 32-bit random values to generate a full 64-bit random long value.
     * It is particularly useful when a larger range than {@link #nextInt()} is required.
     * </p>
     *
     * @return the next random long integer.
     */
    default long nextLong() {
        int upperBits = this.next(32);
        int lowerBits = this.next(32);
        long combinedBits = (long) upperBits << 32;
        return combinedBits + (long) lowerBits;
    }

    /**
     * Generates the next random boolean value.
     *
     * <p>
     * This method uses a single bit of randomness to determine the boolean value.
     * </p>
     *
     * @return the next random boolean value, either {@code true} or {@code false}.
     */
    default boolean nextBoolean() {
        return this.next(1) != 0;
    }

    /**
     * Generates the next random float value between 0.0 (inclusive) and 1.0 (exclusive).
     *
     * <p>
     * This method uses 24 bits of randomness, providing a high precision float.
     * The result is equivalent to a uniform random number generator in the range [0.0, 1.0).
     * </p>
     *
     * @return the next random float value.
     */
    default float nextFloat() {
        return (float) this.next(24) * 5.9604645E-8F;
    }

    /**
     * Generates the next random double value between 0.0 (inclusive) and 1.0 (exclusive).
     *
     * <p>
     * This method uses 53 bits of randomness, providing a high precision double.
     * The result is equivalent to a uniform random number generator in the range [0.0, 1.0).
     * </p>
     *
     * @return the next random double value.
     */
    default double nextDouble() {
        int upperBits = this.next(26);
        int lowerBits = this.next(27);
        long combinedBits = ((long) upperBits << 27) + (long) lowerBits;
        return (double) combinedBits * 1.1102230246251565E-16;
    }
}
