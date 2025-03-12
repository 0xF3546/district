package de.district.api.experimental.random;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Charsets;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.google.common.primitives.Longs;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * The {@code RandomSupport} class provides utility methods for generating and manipulating seeds used
 * in random number generation. It includes methods for upgrading 64-bit seeds to 128-bit seeds, mixing
 * seeds to improve randomness, and generating unique seeds.
 * </p>
 *
 * <p>
 * This class also includes an inner record class, {@code Seed128bit}, which encapsulates a 128-bit seed
 * and provides methods for XOR operations and mixing.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * long uniqueSeed = RandomSupport.generateUniqueSeed();
 * RandomSupport.Seed128bit seed128 = RandomSupport.upgradeSeedTo128bit(uniqueSeed);
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public final class RandomSupport {

    private static final HashFunction MD5_128 = Hashing.md5();
    private static final AtomicLong SEED_UNIQUIFIER = new AtomicLong(8682522807148012L);

    /**
     * Mixes the given 64-bit seed using the Stafford 1.3 mixing function.
     *
     * <p>
     * This method is used to improve the distribution of bits in the seed, reducing
     * the likelihood of patterns or correlations in the generated random numbers.
     * </p>
     *
     * @param seed the 64-bit seed to mix.
     * @return the mixed seed.
     */
    @VisibleForTesting
    public static long mixStafford13(long seed) {
        seed = (seed ^ (seed >>> 30)) * -4658895280553007687L;
        seed = (seed ^ (seed >>> 27)) * -7723592293110705685L;
        return seed ^ (seed >>> 31);
    }

    /**
     * Upgrades a 64-bit seed to a 128-bit seed without mixing the bits.
     *
     * <p>
     * This method generates a 128-bit seed from a 64-bit seed by applying a specific transformation.
     * The resulting 128-bit seed is not mixed and may be mixed later using the {@link Seed128bit#mixed()} method.
     * </p>
     *
     * @param seed the 64-bit seed to upgrade.
     * @return the unmixed 128-bit seed.
     */
    public static Seed128bit upgradeSeedTo128bitUnmixed(final long seed) {
        long seedLo = seed ^ 7640891576956012809L;
        long seedHi = seedLo + -7046029254386353131L;
        return new Seed128bit(seedLo, seedHi);
    }

    /**
     * Upgrades a 64-bit seed to a 128-bit seed with mixed bits.
     *
     * <p>
     * This method generates a 128-bit seed from a 64-bit seed and mixes the bits using
     * the Stafford 1.3 mixing function to improve randomness.
     * </p>
     *
     * @param seed the 64-bit seed to upgrade.
     * @return the mixed 128-bit seed.
     */
    public static Seed128bit upgradeSeedTo128bit(final long seed) {
        return upgradeSeedTo128bitUnmixed(seed).mixed();
    }

    /**
     * Generates a 128-bit seed from the hash of a given string using the MD5 hashing algorithm.
     *
     * <p>
     * This method converts the given string into a 128-bit seed by hashing the string with MD5.
     * </p>
     *
     * @param input the string to hash.
     * @return the 128-bit seed generated from the string's hash.
     */
    public static Seed128bit seedFromHashOf(final String input) {
        byte[] hashBytes = MD5_128.hashString(input, Charsets.UTF_8).asBytes();
        long seedLo = Longs.fromBytes(hashBytes[0], hashBytes[1], hashBytes[2], hashBytes[3], hashBytes[4], hashBytes[5], hashBytes[6], hashBytes[7]);
        long seedHi = Longs.fromBytes(hashBytes[8], hashBytes[9], hashBytes[10], hashBytes[11], hashBytes[12], hashBytes[13], hashBytes[14], hashBytes[15]);
        return new Seed128bit(seedLo, seedHi);
    }

    /**
     * Generates a unique 64-bit seed.
     *
     * <p>
     * This method generates a unique seed using an atomic counter combined with the current time in nanoseconds.
     * </p>
     *
     * @return a unique 64-bit seed.
     */
    public static long generateUniqueSeed() {
        return SEED_UNIQUIFIER.updateAndGet(l -> l * 1181783497276652981L) ^ System.nanoTime();
    }

    /**
     * <p>
     * The {@code Seed128bit} record represents a 128-bit seed composed of two 64-bit components.
     * It provides methods for performing XOR operations and for mixing the seed using the Stafford 1.3 function.
     * </p>
     *
     * <p>
     * Instances of {@code Seed128bit} are immutable and can be used to store and manipulate 128-bit seeds
     * for random number generation.
     * </p>
     */
    public record Seed128bit(long seedLo, long seedHi) {

        /**
         * Constructs a new {@code Seed128bit} instance with the specified lower and upper 64-bit seed components.
         *
         * @param seedLo the lower 64 bits of the seed.
         * @param seedHi the upper 64 bits of the seed.
         */
        public Seed128bit(long seedLo, long seedHi) {
            this.seedLo = seedLo;
            this.seedHi = seedHi;
        }

        /**
         * Performs an XOR operation on this 128-bit seed with the specified 64-bit values.
         *
         * <p>
         * This method returns a new {@code Seed128bit} instance where each 64-bit component
         * has been XORed with the corresponding argument.
         * </p>
         *
         * @param xorLo the value to XOR with the lower 64 bits of the seed.
         * @param xorHi the value to XOR with the upper 64 bits of the seed.
         * @return a new {@code Seed128bit} instance with the XORed values.
         */
        public Seed128bit xor(final long xorLo, final long xorHi) {
            return new Seed128bit(this.seedLo ^ xorLo, this.seedHi ^ xorHi);
        }

        /**
         * Performs an XOR operation on this 128-bit seed with another {@code Seed128bit}.
         *
         * <p>
         * This method returns a new {@code Seed128bit} instance where each 64-bit component
         * has been XORed with the corresponding component of the other {@code Seed128bit}.
         * </p>
         *
         * @param other the other {@code Seed128bit} to XOR with.
         * @return a new {@code Seed128bit} instance with the XORed values.
         */
        public Seed128bit xor(final Seed128bit other) {
            return this.xor(other.seedLo, other.seedHi);
        }

        /**
         * Mixes both 64-bit components of this 128-bit seed using the Stafford 1.3 function.
         *
         * <p>
         * This method returns a new {@code Seed128bit} instance with mixed 64-bit components,
         * which improves the randomness of the seed.
         * </p>
         *
         * @return a new {@code Seed128bit} instance with mixed components.
         */
        public Seed128bit mixed() {
            return new Seed128bit(RandomSupport.mixStafford13(this.seedLo), RandomSupport.mixStafford13(this.seedHi));
        }

        /**
         * Returns the lower 64 bits of this 128-bit seed.
         *
         * @return the lower 64 bits of this seed.
         */
        public long seedLo() {
            return this.seedLo;
        }

        /**
         * Returns the upper 64 bits of this 128-bit seed.
         *
         * @return the upper 64 bits of this seed.
         */
        public long seedHi() {
            return this.seedHi;
        }
    }
}
