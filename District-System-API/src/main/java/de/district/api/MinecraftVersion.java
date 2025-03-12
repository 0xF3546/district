package de.district.api;

import org.jetbrains.annotations.NotNull;

/**
 * Represents a Minecraft version.
 * <p>
 * The MinecraftVersion enum contains a list of Minecraft versions and their corresponding names.
 * Each Minecraft version is identified by a major version number and a name.
 * </p>
 * <p>
 * This enum includes methods to retrieve the name of the Minecraft version,
 * check if the Minecraft version is virtual, check if a given Minecraft version matches the current version,
 * check if the current Minecraft version is at least a specified version,
 * and check if the current Minecraft version is before a specified version.
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public enum MinecraftVersion {

    /**
     * Represents the Minecraft version 1.20.X.
     */
    MINECRAFT_1_20(20, "1.20.X"),
    /**
     * Represents an unknown Minecraft version.
     * <p>
     * This enum constant is used to represent a Minecraft version that is unknown or not yet supported.
     * It is defined in the `MinecraftVersion` enum.
     *
     * @see MinecraftVersion
     */
    UNKNOWN("Unknown", true),
    /**
     * Represents a virtual Minecraft version.
     * <p>
     * This enum constant is used to represent a UNIT_TEST Environment.
     * It is called when a Unit Test is running.
     * Unit Tests are run by the JUnit Framework and MockBukkit.
     * </p>
     *
     * @since 1.0.0
     */
    UNIT_TEST("Unit Test", true);

    /**
     * The variable "name" is a private final String that represents the name of a Minecraft version.
     * It is a field of the MinecraftVersion class.
     * <p>
     * The MinecraftVersion enum contains a list of Minecraft versions and their corresponding names.
     * The "name" field is used to store the name of each Minecraft version.
     * </p>
     * <p>
     * The "isVirtual" method is used to check if a Minecraft version is virtual or not.
     * A virtual Minecraft version is not associated with a specific major version number.
     * </p>
     * <p>
     * Example usage:<br>
     * <code>
     * MinecraftVersion version = MinecraftVersion.MINECRAFT_1_20;
     * String name = version.getName(); // "1.20.X"
     * boolean isVirtual = version.isVirtual(); // false
     * </code>
     * </p>
     *
     * @since 1.0.0
     */
    private final String name;
    /**
     * Flag that indicates whether the Minecraft version is virtual. (Emulated)
     *
     * @apiNote A virtual Minecraft version is not associated with a specific major version number. (e.g. UNIT_TEST)
     * @since 1.0.0
     */
    private final boolean virtual;
    /**
     * Represents the major version of Minecraft.
     *
     * @see MinecraftVersion
     * @since 1.0.0
     */
    private final int majorVersion;

    /**
     * Represents a version of Minecraft. Each version is identified by a major version number and a name.
     *
     * @param majorVersion The major version number of the Minecraft version.
     * @param name         The name of the Minecraft version.
     * @since 1.0.0
     */
    MinecraftVersion(final int majorVersion, @NotNull final String name) {
        this.majorVersion = majorVersion;
        this.name = name;
        this.virtual = false;
    }

    /**
     * Represents a version of Minecraft.
     *
     * @param name    The name of the Minecraft version.
     * @param virtual Whether the Minecraft version is virtual.
     * @since 1.0.0
     */
    MinecraftVersion(@NotNull final String name, final boolean virtual) {
        this.majorVersion = 0;
        this.name = name;
        this.virtual = virtual;
    }

    /**
     * Retrieves the name of the Minecraft version.
     *
     * @return The name of the Minecraft version.
     * @since 1.0.0
     */
    @NotNull
    public String getName() {
        return this.name;
    }

    /**
     * Checks if the Minecraft version is virtual.
     *
     * @return true if the Minecraft version is virtual, false otherwise.
     * @since 1.0.0
     */
    public boolean isVirtual() {
        return this.virtual;
    }

    /**
     * Checks if the given Minecraft version matches the current Minecraft version of the object.
     *
     * @param minecraftVersion The Minecraft version to check against.
     * @return True if the given Minecraft version matches the current Minecraft version, false otherwise.
     * @since 1.0.0
     */
    public boolean isMinecraftVersion(final int minecraftVersion) {
        return !isVirtual() && this.majorVersion == minecraftVersion;
    }

    /**
     * Checks if the current Minecraft version is at least the specified version.
     *
     * @param version The minimum version to check against.
     * @return True if the current version is at least the specified version, false otherwise.
     * @since 1.0.0
     */
    public boolean isAtLeast(@NotNull final MinecraftVersion version) {

        if (this == UNKNOWN) {
            return false;
        }

        if (this == UNIT_TEST && version.ordinal() == 0) {
            throw new IllegalArgumentException("Version " + version + " is the lowest supported version already!");
        }

        return this.ordinal() >= version.ordinal();
    }

    /**
     * Checks if the current Minecraft version is before the specified version.
     *
     * @param version The Minecraft version to compare against.
     * @return {@code true} if the current Minecraft version is before the specified version, {@code false} otherwise.
     * @since 1.0.0
     */
    public boolean isBefore(@NotNull final MinecraftVersion version) {
        if (this == UNKNOWN) {
            return true;
        }

        return version.ordinal() > this.ordinal();
    }
}
