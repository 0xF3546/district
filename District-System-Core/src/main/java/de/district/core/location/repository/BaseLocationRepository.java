package de.district.core.location.repository;

import de.district.core.location.domain.BaseLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * The {@code LocationDao} interface provides data access methods for {@link BaseLocationEntity} objects.
 * This interface extends the {@link JpaRepository} interface, which provides generic CRUD (Create, Read, Update, Delete)
 * operations for the {@code BaseLocationEntity} class.
 *
 * <p>This DAO (Data Access Object) is used to interact with the database and perform operations related to
 * location entities. It defines custom query methods for retrieving locations based on specific criteria, such as
 * the world or name of the location.</p>
 *
 * <p>The {@code LocationDao} interface is automatically implemented by Spring Data JPA at runtime, allowing developers
 * to use these methods without having to write boilerplate SQL queries or implementations.</p>
 *
 * @see BaseLocationEntity
 * @see JpaRepository
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface BaseLocationRepository extends JpaRepository<BaseLocationEntity, Long> {

    /**
     * Retrieves a list of {@link BaseLocationEntity} objects that belong to the specified world.
     *
     * @param world the name of the world to filter locations by.
     * @return a list of {@link BaseLocationEntity} objects that are located in the specified world.
     */
    List<BaseLocationEntity> findByWorld(final String world);

    /**
     * Retrieves a list of {@link BaseLocationEntity} objects that have the specified name.
     *
     * @param name the name of the location to filter by.
     * @return a list of {@link BaseLocationEntity} objects that have the specified name.
     */
    List<BaseLocationEntity> findByName(final String name);
}
