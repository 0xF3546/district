package de.district.core.economy.repository;

import de.district.core.economy.domain.Economy;
import de.district.core.user.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * The {@code EconomyRepository} interface extends {@link CrudRepository} and provides methods for performing CRUD operations
 * on {@link Economy} entities within the database.
 * This interface includes a custom query method for finding an economy
 * record associated with a specific {@link User}.
 *
 * <p>Implementations of this interface are automatically provided by Spring Data at runtime.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface EconomyRepository extends CrudRepository<Economy, Long> {

    /**
     * Finds an economy record associated with the specified {@link User}.
     *
     * @param user the user whose economy record is to be found.
     * @return an {@link Optional} containing the economy record if found, or empty if not.
     */
    Optional<Economy> findByUser(final User user);
}
