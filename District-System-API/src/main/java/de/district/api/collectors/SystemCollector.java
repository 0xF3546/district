package de.district.api.collectors;

import java.util.Properties;

/**
 * The {@code SystemCollector} interface defines the contract for collecting and accessing
 * various system metrics and properties. Implementations of this interface should provide
 * methods to gather information about the operating system, Java Virtual Machine (JVM),
 * memory usage, and other relevant system properties.
 *
 * <p>Typical use cases include monitoring system health, performance analytics, and gathering
 * runtime environment details for logging or diagnostic purposes.</p>
 *
 * <p>Methods provided by this interface should return the latest snapshot of the system's state,
 * and it is expected that an implementation class periodically refreshes the collected data.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public interface SystemCollector {
    /**
     * Gets the name and version of the operating system on which the JVM is running.
     *
     * @return the name and version of the operating system.
     */
    String getOperatingSystem();

    /**
     * Gets the architecture of the operating system.
     *
     * @return the architecture of the operating system.
     */
    String getOsArchitecture();

    /**
     * Gets the number of processors available to the JVM.
     *
     * @return the number of available processors.
     */
    int getAvailableProcessors();

    /**
     * Gets the system load average for the last minute.
     *
     * <p>The load average is a system-specific measure of the average
     * number of runnable entities queued to the available processors.</p>
     *
     * @return the system load average, or a negative value if not available.
     */
    double getSystemLoadAverage();

    /**
     * Gets the name of the Java Virtual Machine (JVM).
     *
     * @return the name of the JVM.
     */
    String getJvmName();

    /**
     * Gets the version of the Java Virtual Machine (JVM).
     *
     * @return the version of the JVM.
     */
    String getJvmVersion();

    /**
     * Gets the vendor name of the Java Virtual Machine (JVM).
     *
     * @return the vendor name of the JVM.
     */
    String getJvmVendor();

    /**
     * Gets the uptime of the Java Virtual Machine (JVM) in milliseconds.
     *
     * @return the uptime of the JVM in milliseconds.
     */
    long getJvmUptime();

    /**
     * Gets the amount of used heap memory in bytes.
     *
     * @return the amount of used heap memory in bytes.
     */
    long getHeapMemoryUsed();

    /**
     * Gets the maximum amount of heap memory that can be used by the JVM in bytes.
     *
     * @return the maximum amount of heap memory in bytes, or -1 if undefined.
     */
    long getHeapMemoryMax();

    /**
     * Gets the amount of used non-heap memory in bytes.
     *
     * @return the amount of used non-heap memory in bytes.
     */
    long getNonHeapMemoryUsed();

    /**
     * Gets the maximum amount of non-heap memory that can be used by the JVM in bytes.
     *
     * @return the maximum amount of non-heap memory in bytes, or -1 if undefined.
     */
    long getNonHeapMemoryMax();

    /**
     * Gets a {@link Properties} object containing key-value pairs representing
     * the system properties of the JVM.
     *
     * <p>System properties include information such as the Java runtime version,
     * file encoding, and user language settings.</p>
     *
     * @return the system properties as a {@link Properties} object.
     */
    Properties getSystemProperties();
}
