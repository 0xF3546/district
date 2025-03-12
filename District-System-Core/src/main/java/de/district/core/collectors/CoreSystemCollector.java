package de.district.core.collectors;

import de.district.api.collectors.SystemCollector;
import lombok.Getter;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

/**
 * The {@code CoreSystemCollector} class provides an implementation of the {@link SystemCollector} interface
 * for gathering and storing system metrics and properties. This class collects information about
 * the operating system, Java Virtual Machine (JVM), and memory usage.
 *
 * <p>This class uses the Java Management Extensions (JMX) API to retrieve system information
 * and stores the data in instance variables. The data can be updated by calling the {@link #update()}
 * method, which refreshes the stored values with the latest metrics.</p>
 *
 * <p>The {@code CoreSystemCollector} is intended to be used in enterprise applications where
 * monitoring of system resources is crucial, such as in performance analysis, diagnostics, or logging systems.</p>
 *
 * <p>It is recommended to update the collected data periodically, depending on the needs of the application.</p>
 *
 * @see SystemCollector
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
public class CoreSystemCollector implements SystemCollector {

    private String operatingSystem;
    private String osArchitecture;
    private int availableProcessors;
    private double systemLoadAverage;

    private String jvmName;
    private String jvmVersion;
    private String jvmVendor;
    private long jvmUptime;

    private long heapMemoryUsed;
    private long heapMemoryMax;

    private long nonHeapMemoryUsed;
    private long nonHeapMemoryMax;

    private Properties systemProperties;

    /**
     * Updates the system information metrics by collecting the latest data
     * from the operating system and JVM.
     *
     * <p>This method should be called periodically to ensure that the stored
     * metrics reflect the current state of the system.</p>
     */
    public SystemCollector update() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        this.operatingSystem = osBean.getName() + " " + osBean.getVersion();
        this.osArchitecture = osBean.getArch();
        this.availableProcessors = osBean.getAvailableProcessors();
        this.systemLoadAverage = osBean.getSystemLoadAverage();

        this.jvmName = runtimeBean.getVmName();
        this.jvmVersion = runtimeBean.getVmVersion();
        this.jvmVendor = runtimeBean.getVmVendor();
        this.jvmUptime = runtimeBean.getUptime();

        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        this.heapMemoryUsed = heapMemoryUsage.getUsed();
        this.heapMemoryMax = heapMemoryUsage.getMax();

        MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();
        this.nonHeapMemoryUsed = nonHeapMemoryUsage.getUsed();
        this.nonHeapMemoryMax = nonHeapMemoryUsage.getMax();

        this.systemProperties = System.getProperties();
        return this;
    }
}
