package de.district.core.util;

/**
 * The {@code ConvertingUtils} class provides a collection of utility methods for converting
 * data sizes (in bytes) and time durations (in milliseconds) into human-readable formats.
 *
 * <p>This class is designed to be a utility class with static methods, meaning it cannot
 * be instantiated. It provides methods for converting bytes into binary (e.g., KiB, MiB)
 * and decimal (e.g., KB, MB) formats, as well as for converting milliseconds into
 * a human-readable time format.</p>
 *
 * <p>These utility methods are useful in scenarios where system metrics or other
 * numerical data need to be presented in a format that is easy for users to understand.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public class ConvertingUtils {

    private static final int BINARY_MULTIPLIER = 1024;
    private static final int DECIMAL_MULTIPLIER = 1000;

    /**
     * Converts a size in bytes to a human-readable string using binary multiples (e.g., KiB, MiB).
     *
     * <p>If the size is less than 1024 bytes, the value is returned in bytes (B). Otherwise,
     * the size is converted to the nearest binary unit (KiB, MiB, etc.) with one decimal place of precision.</p>
     *
     * @param bytes the size in bytes to convert.
     * @return a human-readable string representing the size in binary units.
     */
    public static String convertBytesToHumanReadable(long bytes) {
        if (bytes < BINARY_MULTIPLIER) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(BINARY_MULTIPLIER));
        char pre = "kMGTPE".charAt(exp - 1);
        return String.format("%.1f %siB", bytes / Math.pow(BINARY_MULTIPLIER, exp), pre);
    }

    /**
     * Converts a size in bytes to a human-readable string using either binary (e.g., KiB, MiB)
     * or decimal multiples (e.g., KB, MB), depending on the specified mode.
     *
     * <p>If {@code decimal} is {@code true}, the size is converted using decimal multiples
     * (1000 bytes per unit). If {@code false}, binary multiples (1024 bytes per unit) are used.</p>
     *
     * @param bytes the size in bytes to convert.
     * @param decimal whether to use decimal multiples (true) or binary multiples (false).
     * @return a human-readable string representing the size in the chosen format.
     */
    public static String convertBytesToHumanReadable(long bytes, boolean decimal) {
        if (!decimal) {
            return convertBytesToHumanReadable(bytes);
        }
        if (bytes < DECIMAL_MULTIPLIER) {
            return bytes + " B";
        }
        int exp = (int) (Math.log(bytes) / Math.log(DECIMAL_MULTIPLIER));
        char pre = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sB", bytes / Math.pow(DECIMAL_MULTIPLIER, exp), pre);
    }

    /**
     * Converts a duration in milliseconds to a human-readable string.
     *
     * <p>The conversion follows these rules:</p>
     * <ul>
     *   <li>Less than 60 seconds: returned as seconds (s).</li>
     *   <li>Less than 60 minutes: returned as minutes (m).</li>
     *   <li>Less than 24 hours: returned as hours (h).</li>
     *   <li>24 hours or more: returned as days (d).</li>
     * </ul>
     *
     * @param milliseconds the duration in milliseconds to convert.
     * @return a human-readable string representing the duration.
     */
    public static String convertMillisecondsToHumanReadable(long milliseconds) {
        long seconds = milliseconds / 1000;
        if (seconds < 60) {
            return seconds + "s";
        }
        long minutes = seconds / 60;
        if (minutes < 60) {
            return minutes + "m";
        }
        long hours = minutes / 60;
        if (hours < 24) {
            return hours + "h";
        }
        long days = hours / 24;
        return days + "d";
    }
}
