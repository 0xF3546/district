/**
 * <p>
 * The {@code de.district.api.experimental.random} package provides a suite of classes and interfaces
 * for high-quality random number generation, with a focus on experimental and legacy algorithms. This package includes
 * implementations tailored for both single-threaded and multi-threaded environments, supporting a wide range of use cases
 * from simple random number generation to complex procedural content generation.
 * </p>
 *
 * <h2>Key Components:</h2>
 * <ul>
 *     <li>{@link de.district.api.experimental.random.BitRandomSource BitRandomSource} - The base interface for generating random numbers with a specified bit length. Implementations include both thread-safe and single-threaded variants.</li>
 *     <li>{@link de.district.api.experimental.random.SingleThreadedRandomSource SingleThreadedRandomSource} - A simple, non-thread-safe random number generator designed for single-threaded applications.</li>
 *     <li>{@link de.district.api.experimental.random.ThreadSafeLegacyRandomSource ThreadSafeLegacyRandomSource} - A thread-safe, legacy random number generator that is deprecated in favor of modern alternatives.</li>
 *     <li>{@link de.district.api.experimental.random.MarsagliaPolarGaussian MarsagliaPolarGaussian} - An implementation of the Marsaglia polar method for generating Gaussian-distributed random numbers.</li>
 *     <li>{@link de.district.api.experimental.random.PositionalRandomFactory PositionalRandomFactory} - An interface for generating random sources tied to specific positions or hashes, useful in procedural generation.</li>
 *     <li>{@link de.district.api.experimental.random.RandomSupport RandomSupport} - Utility class for seed manipulation, including upgrading seeds to 128-bit values and generating unique seeds.</li>
 *     <li>{@link de.district.api.experimental.random.ThreadingDetector ThreadingDetector} - A utility class for detecting and managing threading issues in random number generation.</li>
 * </ul>
 *
 * <h2>Design Considerations:</h2>
 * <p>
 * The classes and interfaces in this package are designed with the following principles in mind:
 * </p>
 * <ul>
 *     <li><b>Flexibility:</b> The package supports various random number generation needs, from simple cases to more complex, procedural requirements.</li>
 *     <li><b>Performance:</b> Implementations like {@link de.district.api.experimental.random.ThreadSafeLegacyRandomSource ThreadSafeLegacyRandomSource} are optimized for performance in single-threaded environments, while {@link de.district.api.experimental.random.ThreadSafeLegacyRandomSource ThreadSafeLegacyRandomSource} provides thread safety at the cost of additional overhead.</li>
 *     <li><b>Compatibility:</b> The inclusion of legacy support ensures that older systems and applications can continue to function while being phased out in favor of more modern techniques.</li>
 *     <li><b>Reproducibility:</b> Seed management across the package ensures that random sequences can be reproduced consistently, which is critical for applications in testing and procedural generation.</li>
 * </ul>
 *
 * <h2>Deprecated Features:</h2>
 * <p>
 * The package includes several classes and methods that are marked as deprecated, reflecting the ongoing evolution of random number generation techniques. These deprecated features are maintained for backward compatibility but are not recommended for new development:
 * </p>
 * <ul>
 *     <li>{@link de.district.api.experimental.random.ThreadSafeLegacyRandomSource ThreadSafeLegacyRandomSource} - A thread-safe random generator that is deprecated in favor of more efficient modern alternatives.</li>
 *     <li>{@link de.district.api.experimental.random.RandomSource#createThreadSafe() RandomSource#createThreadSafe()} - A method for creating thread-safe random sources that is no longer recommended.</li>
 * </ul>
 *
 * <h2>Usage Guidelines:</h2>
 * <p>
 * When using the classes and interfaces in this package, consider the following best practices:
 * </p>
 * <ul>
 *     <li><b>Thread Safety:</b> Ensure that you choose the appropriate random source implementation based on your application's threading requirements. Use {@link de.district.api.experimental.random.SingleThreadedRandomSource SingleThreadedRandomSource} in single-threaded contexts and avoid using deprecated thread-safe implementations in new projects.</li>
 *     <li><b>Seed Management:</b> Leverage the utilities in {@link de.district.api.experimental.random.RandomSupport RandomSupport} for generating and manipulating seeds, especially when dealing with procedural content generation where consistency and reproducibility are paramount.</li>
 *     <li><b>Monitoring and Debugging:</b> Use {@link de.district.api.experimental.random.ThreadingDetector ThreadingDetector} to detect and resolve potential threading issues during development and testing, ensuring that your application handles random number generation in a thread-safe manner.</li>
 *     <li><b>Migration from Legacy Systems:</b> For applications transitioning from older random number generation systems, use the legacy support classes provided, but plan for eventual migration to more modern approaches.</li>
 * </ul>
 *
 * <h2>Conclusion:</h2>
 * <p>
 * The {@code de.district.api.experimental.random} package offers a robust and flexible set of tools for random number generation, with a balance of legacy support and modern best practices. While some components are marked as deprecated, they serve as a bridge to more efficient and scalable solutions. As the field of random number generation evolves, this package provides the necessary building blocks to ensure that your applications can generate high-quality randomness efficiently and reliably.
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
package de.district.api.experimental.random;
