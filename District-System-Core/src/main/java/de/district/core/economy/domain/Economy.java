package de.district.core.economy.domain;

import de.district.core.economy.domain.dto.EconomyDto;
import de.district.core.user.domain.User;
import de.splatgames.generators.annotation.dto.Dto;
import de.splatgames.validations.valids.api.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

/**
 * The {@code Economy} class represents an economy entity in the system, which is mapped to the {@code economy} table in the database.
 * This class is designed to store and manage economy-related data for users, such as their balance.
 * It provides functionality to convert between the {@link Economy} entity and the {@link EconomyDto} data transfer object (DTO).
 *
 * <p>The {@code Economy} class is annotated with Lombok annotations to generate boilerplate code such as getters, setters,
 * a no-argument constructor, and a {@code toString} method. It is also annotated as an {@link Entity} and is associated with
 * the {@code economy} table in the database using JPA annotations.</p>
 *
 * @see EconomyDto
 * @see Entity
 * @see Getter
 * @see Setter
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "economy")
public class Economy {

    /**
     * The unique identifier for the economy entry, which is generated automatically.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The {@link User} associated with this economy entry. This field is annotated with {@link Dto}
     * to ensure it is included in the corresponding DTO.
     */
    @Dto(order = 1)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * The balance associated with the user in this economy entry. This field is required
     * and is included in the corresponding DTO.
     */
    @Dto(order = 2)
    @Column(name = "balance", nullable = false)
    private double balance;

    /**
     * Constructs a new {@code Economy} entity based on the provided {@link EconomyDto}.
     *
     * @param economyDto the data transfer object containing the economy data.
     */
    public Economy(final EconomyDto economyDto) {
        change(economyDto);
    }

    /**
     * Updates the current {@link Economy} entity with data from the provided {@link EconomyDto}.
     *
     * <p>This method performs validation to ensure that the {@code economyDto} is not null before updating the fields.</p>
     *
     * @param economyDto the data transfer object containing the updated economy data.
     * @throws NullPointerException if {@code economyDto} is {@code null}.
     */
    public void change(final EconomyDto economyDto) {
        Validations.assertThat(economyDto).isNotNull();

        this.user = economyDto.getUser();
        this.balance = economyDto.getBalance();
    }

    /**
     * Builds and returns an {@link EconomyDto} based on the current state of the {@link Economy} entity.
     *
     * @return a new {@link EconomyDto} containing the economy data.
     */
    public EconomyDto buildEconomyDto() {
        return new EconomyDto(this.user,
                this.balance);
    }

    /**
     * Compares this {@link Economy} entity to another object for equality.
     *
     * <p>This method ensures that equality is based on the economy entry's unique identifier, and it handles cases where
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
        Economy economy = (Economy) o;
        return getId() != null && Objects.equals(getId(), economy.getId());
    }

    /**
     * Returns the hash code for this {@link Economy} entity.
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
