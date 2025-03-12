package de.district.core.location.domain;

import de.district.api.location.Location;
import de.district.api.location.LocationType;
import de.district.core.location.domain.dto.BaseLocationEntityDto;
import de.district.core.location.domain.dto.CoordinateDto;
import de.splatgames.generators.annotation.dto.Dto;
import de.splatgames.validations.valids.api.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * The {@code BaseLocationEntity} class serves as the abstract base class for all location entities in the system.
 * It is mapped to the {@code locations} table in the database, and it uses a single-table inheritance strategy to
 * accommodate various types of locations.
 *
 * <p>This class implements the {@link Location} interface and provides common properties and methods that are shared
 * among different types of location entities. Subclasses of {@code BaseLocationEntity} represent specific types of locations
 * and are distinguished in the database by the {@code location_type} discriminator column.</p>
 *
 * <p>The class is annotated with Lombok annotations to generate boilerplate code such as getters, setters,
 * a no-argument constructor, and a {@code toString} method. It also includes methods for converting between the
 * {@code BaseLocationEntity} entity and data transfer objects (DTOs) such as {@link BaseLocationEntityDto} and {@link CoordinateDto}.</p>
 *
 * @see Location
 * @see BaseLocationEntityDto
 * @see CoordinateDto
 * @since 1.0.0
 * @author Erik Pförtner
 */
@ToString
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "locations")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "location_type")
public abstract class BaseLocationEntity implements Location {

    /**
     * The unique identifier for the location, which is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the location. This field is required and is included in the corresponding DTO.
     */
    @Dto(order = 1)
    private String name;

    /**
     * The world in which the location is situated. This field is required and is included in the corresponding DTO.
     */
    @Dto(order = 2)
    private String world;

    /**
     * The type of location, represented by an enum. This field is set automatically based on the subclass type
     * and is included in the corresponding DTO.
     */
    @Dto(order = 3)
    @Enumerated(EnumType.STRING)
    @Column(name = "location_type", insertable = false, updatable = false)
    private LocationType type;

    /**
     * The x-coordinate of the location. This field is required and is included in the corresponding DTO.
     */
    @Dto(order = 1, value = "Coordinate")
    private double x;

    /**
     * The y-coordinate of the location. This field is required and is included in the corresponding DTO.
     */
    @Dto(order = 2, value = "Coordinate")
    private double y;

    /**
     * The z-coordinate of the location. This field is required and is included in the corresponding DTO.
     */
    @Dto(order = 3, value = "Coordinate")
    private double z;

    /**
     * Constructs a new {@code BaseLocationEntity} entity based on the provided {@link BaseLocationEntityDto} and {@link CoordinateDto}.
     *
     * @param baseLocationEntityDto the data transfer object containing the basic location data.
     * @param coordinateDto the data transfer object containing the coordinate data.
     */
    public BaseLocationEntity(final BaseLocationEntityDto baseLocationEntityDto, final CoordinateDto coordinateDto) {
        change(baseLocationEntityDto);
        change(coordinateDto);
    }

    /**
     * Updates the current {@code BaseLocationEntity} entity with data from the provided {@link BaseLocationEntityDto}.
     *
     * <p>This method performs validation to ensure that the {@code baseLocationEntityDto} is not null before updating the fields.</p>
     *
     * @param baseLocationEntityDto the data transfer object containing the updated basic location data.
     * @throws NullPointerException if {@code baseLocationEntityDto} is {@code null}.
     */
    public void change(final BaseLocationEntityDto baseLocationEntityDto) {
        Validations.assertThat(baseLocationEntityDto).isNotNull();

        this.name = baseLocationEntityDto.getName();
        this.world = baseLocationEntityDto.getWorld();
        this.type = baseLocationEntityDto.getType();
    }

    /**
     * Builds and returns a {@link BaseLocationEntityDto} based on the current state of the {@code BaseLocationEntity} entity.
     *
     * @return a new {@link BaseLocationEntityDto} containing the basic location data.
     */
    public BaseLocationEntityDto buildBaseLocationEntityDto() {
        return new BaseLocationEntityDto(this.name,
                this.world,
                this.type);
    }

    /**
     * Updates the current {@code BaseLocationEntity} entity with data from the provided {@link CoordinateDto}.
     *
     * <p>This method performs validation to ensure that the {@code coordinateDto} is not null before updating the fields.</p>
     *
     * @param coordinateDto the data transfer object containing the updated coordinate data.
     * @throws NullPointerException if {@code coordinateDto} is {@code null}.
     */
    public void change(final CoordinateDto coordinateDto) {
        Validations.assertThat(coordinateDto).isNotNull();

        this.x = coordinateDto.getX();
        this.y = coordinateDto.getY();
        this.z = coordinateDto.getZ();
    }

    /**
     * Builds and returns a {@link CoordinateDto} based on the current state of the {@code BaseLocationEntity} entity.
     *
     * @return a new {@link CoordinateDto} containing the coordinate data.
     */
    public CoordinateDto buildCoordinateDto() {
        return new CoordinateDto(this.x,
                this.y,
                this.z);
    }

    /**
     * Compares this {@code BaseLocationEntity} entity to another object for equality.
     *
     * <p>This method ensures that equality is based on the location's unique identifier, and it handles cases where
     * the entity is a proxy object generated by Hibernate.</p>
     *
     * @param o the object to compare to.
     * @return {@code true} if the objects are considered equal, {@code false} otherwise.
     */
    @Override
    public final boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BaseLocationEntity that = (BaseLocationEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    /**
     * Returns the hash code for this {@code BaseLocationEntity} entity.
     *
     * <p>The hash code is generated based on the class's unique identifier and handles cases where the entity
     * is a proxy object generated by Hibernate.</p>
     *
     * @return the hash code of this entity.
     */
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
