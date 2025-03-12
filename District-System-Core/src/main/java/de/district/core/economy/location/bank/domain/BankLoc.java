package de.district.core.economy.location.bank.domain;

import de.district.api.location.LocationType;
import de.district.core.location.domain.BaseLocationEntity;
import de.splatgames.generators.annotation.dto.Dto;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "banks")
@DiscriminatorValue("BANK")
public class BankLoc extends BaseLocationEntity {

    @Dto(order = 1)
    @Column(name = "provider")
    private String provider;

    public BankLoc() {
        super.setType(LocationType.BANK);
    }
}
