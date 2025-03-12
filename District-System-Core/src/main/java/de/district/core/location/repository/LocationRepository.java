package de.district.core.location.repository;

import de.district.core.location.domain.BaseLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocationRepository<ENTITY extends BaseLocationEntity, ID> extends JpaRepository<ENTITY, ID> {
    List<ENTITY> findByName(final String name);

    List<ENTITY> findByWorld(final String world);
}
