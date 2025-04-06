package de.district.core.character.util;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

/**
 * Enum representing genders with their associated character codes.
 * <p>
 * This enum provides two gender constants, {@link #MALE} and {@link #FEMALE},
 * each associated with a unique character code. The enum also provides a method
 * to retrieve the corresponding {@link Gender} instance from a character code.
 */
@Getter
public enum Gender {
    MALE('M'),
    FEMALE('F');

    /**
     * The character code representing the gender.
     * For example, 'M' for male and 'F' for female.
     */
    private final char genderCode;

    Gender(final char genderCode) {
        this.genderCode = genderCode;
    }

    /**
     * Retrieves the {@link Gender} instance corresponding to the given character code.
     *
     * @param genderCode The character code of the gender (e.g., 'M' for male, 'F' for female).
     * @return The corresponding {@link Gender} instance if found, otherwise {@code null}.
     */
    @Nullable
    @Contract(pure = true)
    public static Gender fromChar(final char genderCode) {
        for (Gender gender : values()) {
            if (gender.getGenderCode() == genderCode) {
                return gender;
            }
        }
    
        return null;
    }
}
