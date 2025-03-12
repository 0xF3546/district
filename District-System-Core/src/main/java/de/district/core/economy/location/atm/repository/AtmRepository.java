package de.district.core.economy.location.atm.repository;

import de.district.core.economy.location.atm.domain.Atm;
import de.district.core.location.repository.LocationRepository;

import java.util.List;

/**
 * The {@code AtmRepository} interface extends {@link LocationRepository} and provides methods for performing CRUD operations
 * on {@link Atm} entities within the database.
 * This interface includes a custom query method for finding ATMs
 * by their provider.
 *
 * <p>Implementations of this interface are automatically provided by Spring Data JPA at runtime.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface AtmRepository extends LocationRepository<Atm, Long> {

    /**
     * Finds all {@link Atm} instances associated with the specified provider.
     *
     * @param provider the provider whose ATMs are to be found.
     * @return a list of ATMs associated with the specified provider.
     */
    List<Atm> findByProvider(final String provider);
}
