package de.district.core.economy.location.atm.domain;

import de.district.api.location.LocationType;
import de.district.core.economy.location.atm.domain.dto.AtmDto;
import de.district.core.location.domain.BaseLocationEntity;
import de.district.core.location.domain.dto.BaseLocationEntityDto;
import de.district.core.location.domain.dto.CoordinateDto;
import de.splatgames.generators.annotation.dto.Dto;
import de.splatgames.validations.valids.api.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The {@code Atm} class represents an ATM (Automated Teller Machine) entity within the game.
 * It extends the {@link BaseLocationEntity} class and includes additional properties specific to ATMs,
 * such as the provider and the amount of money available.
 *
 * <p>This class is mapped to a database table named "atms" and uses a discriminator value "ATM"
 * to differentiate it from other types of {@link BaseLocationEntity}.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "atms")
@DiscriminatorValue("ATM")
public class Atm extends BaseLocationEntity {

    @Dto(order = 1)
    @Column(name = "provider")
    private String provider;

    @Dto(order = 2)
    @Column(name = "available_money")
    private double availableMoney;

    /**
     * Default constructor that sets the location type to {@link LocationType#ATM}.
     */
    public Atm() {
        super.setType(LocationType.ATM);
    }

    /**
     * Constructs a new {@code Atm} entity from the given DTOs.
     *
     * @param baseLocationEntityDto the base location data.
     * @param coordinateDto the coordinates of the ATM.
     * @param atmDto the ATM-specific data.
     */
    public Atm(final BaseLocationEntityDto baseLocationEntityDto, final CoordinateDto coordinateDto, final AtmDto atmDto) {
        baseLocationEntityDto.setType(LocationType.ATM);
        super.change(baseLocationEntityDto);
        super.change(coordinateDto);
        change(atmDto);
    }

    /**
     * Updates the state of this {@code Atm} entity using the data from the given {@link AtmDto}.
     *
     * @param atmDto the DTO containing the updated data.
     */
    public void change(final AtmDto atmDto) {
        Validations.assertThat(atmDto).isNotNull();

        this.provider = atmDto.getProvider();
        this.availableMoney = atmDto.getAvailableMoney();
    }

    /**
     * Converts this {@code Atm} entity to its {@link AtmDto} representation.
     *
     * @return the {@code AtmDto} representing this ATM entity.
     */
    public AtmDto buildAtmDto() {
        return new AtmDto(this.provider, this.availableMoney);
    }
}
