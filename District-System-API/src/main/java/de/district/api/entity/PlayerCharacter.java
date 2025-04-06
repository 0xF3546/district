package de.district.api.entity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;

/**
 * Represents a user-character entity with details such as first name, last name, gender,
 * date of birth, and the date the entity was created. This interface provides a structured
 * way to access these attributes for any implementing class.
 * <p>
 * This entity is commonly used in user profile management and tracking demographic details.
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public interface PlayerCharacter {

    /**
     * Retrieves the first name of the user.
     *
     * @return the first name of the user. Cannot be null or empty.
     */
    @NotNull
    String getFirstName();

    /**
     * Retrieves the last name of the user.
     *
     * @return the last name of the user. Cannot be null or empty.
     */
    @NotNull
    String getLastName();

    /**
     * Retrieves the gender of the user as a single character.
     * <p>
     * Common values include 'M' for Male, 'F' for Female, or 'O' for Other.
     * </p>
     *
     * @return the gender of the user.
     */
    char getGender();

    /**
     * Retrieves the date of birth of the user.
     *
     * @return the date and time of birth as a {@link LocalDateTime} object. Cannot be null.
     */
    @NotNull
    LocalDateTime getDateOfBirth();

    /**
     * Retrieves the timestamp when the user entity was created.
     *
     * @return the creation timestamp as a {@link LocalDateTime} object. Cannot be null.
     */
    @NotNull
    LocalDateTime getCreatedAt();
}
