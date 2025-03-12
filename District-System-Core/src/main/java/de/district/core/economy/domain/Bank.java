package de.district.core.economy.domain;

import de.district.api.economy.BankType;
import de.district.api.economy.bank.Bic;
import de.district.api.economy.bank.Iban;
import de.district.core.economy.converter.BicConverter;
import de.district.core.economy.converter.IbanConverter;
import de.district.core.economy.domain.dto.BankDto;
import de.district.core.user.domain.User;
import de.splatgames.generators.annotation.dto.Dto;
import de.splatgames.validations.valids.api.Validations;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

/**
 * The {@code Bank} class represents a bank entity within the economy system.
 * It is linked to a {@link User} and contains
 * details such as the bank provider, bank type, IBAN, and BIC.
 * This class includes methods to convert between its DTO
 * representation and to validate its state.
 *
 * <p>This class is annotated with JPA annotations to map it to a database table named "bank" and with Lombok annotations
 * to generate boilerplate code such as getters, setters, and constructors.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Dto(order = 1)
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Dto(order = 2)
    @Column(name = "provider")
    private String provider;

    @Dto(order = 3)
    @Column(name = "bank_type")
    private BankType bankType;

    @Dto(order = 4)
    @Column(name = "iban")
    @Convert(converter = IbanConverter.class)
    private Iban iban;

    @Dto(order = 5)
    @Column(name = "bic")
    @Convert(converter = BicConverter.class)
    private Bic bic;

    /**
     * Constructs a new {@code Bank} entity from the given {@link BankDto}.
     *
     * @param bankDto the DTO containing the data to initialize the bank entity.
     */
    public Bank(final BankDto bankDto) {
        change(bankDto);
    }

    /**
     * Updates the state of this {@code Bank} entity using the data from the given {@link BankDto}.
     *
     * @param bankDto the DTO containing the updated data.
     */
    public void change(final BankDto bankDto) {
        Validations.assertThat(bankDto).isNotNull();

        this.user = bankDto.getUser();
        this.provider = bankDto.getProvider();
        this.bankType = bankDto.getBankType();
        this.iban = bankDto.getIban();
        this.bic = bankDto.getBic();
    }

    /**
     * Converts this {@code Bank} entity to its {@link BankDto} representation.
     *
     * @return the {@code BankDto} representing this bank entity.
     */
    public BankDto buildBankDto() {
        return new BankDto(this.user,
                this.provider,
                this.bankType,
                this.iban,
                this.bic);
    }
}
