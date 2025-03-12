package de.district.api.experimental.random;

/**
 * <p>
 * The {@code Mth} class provides utility methods for common mathematical operations
 * that are used in random number generation and other calculations.
 * </p>
 *
 * <p>
 * This class includes methods for squaring a double value and for generating a seed
 * based on integer coordinates. The seed generation method is marked as deprecated
 * due to the potential for collisions and the use of an outdated algorithm.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * double squaredValue = Mth.square(5.0);
 * long seed = Mth.getSeed(10, 20, 30); // Deprecated method
 * }</pre>
 * </p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public class Mth {

    /**
     * Squares the specified double value.
     *
     * <p>
     * This method returns the result of multiplying the given double value by itself.
     * It is commonly used in algorithms that require the square of a number, such as in
     * the calculation of distances or in random number generation techniques like the
     * Marsaglia polar method.
     * </p>
     *
     * @param value the double value to be squared.
     * @return the square of the specified double value.
     */
    public static double square(final double value) {
        return value * value;
    }

    /**
     * Generates a pseudo-random seed based on the specified 3D coordinates.
     *
     * <p>
     * This method uses a simple hash function to generate a seed from the provided
     * integer coordinates. However, due to the potential for hash collisions and the
     * use of an outdated algorithm, this method has been marked as deprecated.
     * </p>
     *
     * <p>
     * <b>Note:</b> It is recommended to use more modern and robust methods for seed
     * generation in new implementations.
     * </p>
     *
     * @param x the X-coordinate.
     * @param y the Y-coordinate.
     * @param z the Z-coordinate.
     * @return a pseudo-random seed generated from the coordinates.
     * @deprecated This method is deprecated due to potential hash collisions and the use
     * of an outdated algorithm. Consider using a more robust seed generation method.
     */
    @Deprecated
    public static long getSeed(final int x, final int y, final int z) {
        long seed = (x * 3129871L) ^ (long) z * 116129781L ^ (long) y;
        seed = seed * seed * 42317861L + seed * 11L;
        return seed >> 16;
    }
}
