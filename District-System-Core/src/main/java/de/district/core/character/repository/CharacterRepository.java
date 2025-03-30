package de.district.core.character.repository;

import de.district.core.character.domain.Character;
import de.district.core.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The {@code CharacterRepository} interface provides methods for interacting with the character repository.
 * <p>
 * This interface extends the {@link CrudRepository} interface, which provides basic CRUD operations for the
 * {@link Character} entity. It includes additional methods for finding characters by user.
 * </p>
 * <p>
 * The {@code CharacterRepository} interface is used by the application to interact with the character repository
 * and perform operations such as saving, updating, and deleting characters.
 * </p>
 *
 * @see CrudRepository
 * @see Character
 * @since 1.0.0
 */
public interface CharacterRepository extends CrudRepository<Character, Long> {

    /**
     * Finds a character by the associated user.
     *
     * @param user The user associated with the character.
     * @return An optional containing the character if found, or an empty optional otherwise.
     */
    Optional<Character> findByUser(final User user);
}
