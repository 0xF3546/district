package de.district.core.user.repository;

import de.district.core.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The {@code UserRepository} interface extends {@link CrudRepository} and provides methods for performing CRUD operations
 * on {@link User} entities within the database.
 * This interface includes a custom query method for finding a user
 * by their unique UUID.
 *
 * <p>Spring Data automatically provide implementations of this interface at runtime.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Finds a {@link User} by their unique UUID.
     *
     * @param uuid the UUID of the user to be found.
     * @return an {@link Optional} containing the user if found, or empty if not.
     */
    Optional<User> findByUuid(final String uuid);
}
