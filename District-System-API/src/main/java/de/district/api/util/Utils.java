package de.district.api.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
public class Utils {
    public static String localDateTimeToString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return localDateTime.format(formatter);
    }

    public static String localDateTimeToReadableString(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM | HH:mm");
        return localDateTime.format(formatter);
    }

    public static LocalDateTime getTime() {
        return LocalDateTime.now(ZoneId.of("Europe/Berlin"));
    }

    public static String toDecimalFormat(int number) {
        return new DecimalFormat("#,###").format(number);
    }

    public static int roundUpToMultipleOfNine(int num) {
        return ((num + 8) / 9) * 9;
    }

    public static boolean isRandom(int chance) {
        int randomNumber = ThreadLocalRandom.current().nextInt(100 + 1);

        return randomNumber < chance;
    }

    public static Location getLocation(int x, int y, int z) {
        return getLocation(x, y, z, Bukkit.getWorld("world"));
    }

    public static Location getLocation(int x, int y, int z, World world) {
        return getLocation(x, y, z, world, 0, 0);
    }

    public static Location getLocation(int x, int y, int z, World world, float yaw, float pitch) {
        return new Location(world, x, y, z, yaw, pitch);
    }
}
