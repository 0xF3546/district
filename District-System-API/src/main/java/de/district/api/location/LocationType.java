package de.district.api.location;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The {@code LocationType} enum defines the different types of locations that can exist within the system.
 * Each type is associated with a specific name, which can be used to identify and categorize locations.
 *
 * <p>Currently, this enum defines the following location types:</p>
 * <ul>
 *     <li>{@link #ATM} - Represents an ATM location.</li>
 *     <li>{@link #BANK} - Represents a Bank location.</li>
 * </ul>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public enum LocationType {

    /**
     * Represents an ATM location.
     */
    ATM("ATM"),

    /**
     * Represents a Bank location.
     */
    BANK("Bank");

    private final String name;

    /**
     * Constructs a new {@code LocationType} with the specified name.
     *
     * @param name the name of the location type, must not be {@code null}.
     */
    LocationType(@NotNull final String name) {
        this.name = name;
    }

    /**
     * Returns the {@code LocationType} corresponding to the specified name.
     * The comparison is case-insensitive.
     *
     * <p>If the name is {@code null}, this method returns {@code null}. If no matching
     * {@code LocationType} is found, {@code null} is also returned.</p>
     *
     * @param name the name of the location type to retrieve, may be {@code null}.
     * @return the {@code LocationType} corresponding to the name, or {@code null} if no match is found.
     */
    @Nullable
    @Contract("null -> null")
    public static LocationType fromName(@Nullable final String name) {
        if (name == null) {
            return null;
        }

        for (final LocationType type : values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     * Retrieves the name of this location type.
     *
     * @return the name of the location type, never {@code null}.
     */
    @NotNull
    public String getName() {
        return this.name;
    }
}
