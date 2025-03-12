package de.district.core.economy.repository;

import de.district.api.economy.BankType;
import de.district.api.economy.bank.Bic;
import de.district.api.economy.bank.Iban;
import de.district.core.economy.domain.Bank;
import de.district.core.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * The {@code BankRepository} interface extends {@link JpaRepository} and provides methods for performing CRUD operations
 * on {@link Bank} entities within the database. This interface includes custom query methods for finding banks
 * by user, provider, {@link BankType}, {@link Iban}, and {@link Bic}.
 *
 * <p>Implementations of this interface are automatically provided by Spring Data JPA at runtime.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
public interface BankRepository extends JpaRepository<Bank, Long> {

    /**
     * Finds a bank associated with the specified {@link User}.
     *
     * @param user the user whose bank is to be found.
     * @return an {@link Optional} containing the bank if found, or empty if not.
     */
    Optional<Bank> findByUser(final User user);

    /**
     * Finds all banks associated with the specified provider.
     *
     * @param provider the provider whose banks are to be found.
     * @return a list of banks associated with the specified provider.
     */
    List<Bank> findByProvider(final String provider);

    /**
     * Finds all banks associated with the specified provider and {@link BankType}.
     *
     * @param provider the provider whose banks are to be found.
     * @param bankType the type of bank to be found.
     * @return a list of banks associated with the specified provider and bank type.
     */
    List<Bank> findByProviderAndBankType(final String provider, final BankType bankType);

    /**
     * Finds a bank associated with the specified {@link Iban}.
     *
     * @param iban the IBAN of the bank to be found.
     * @return an {@link Optional} containing the bank if found, or empty if not.
     */
    Optional<Bank> findByIban(final Iban iban);

    /**
     * Finds a bank associated with the specified {@link Bic}.
     *
     * @param bic the BIC of the bank to be found.
     * @return an {@link Optional} containing the bank if found, or empty if not.
     */
    Optional<Bank> findByBic(final Bic bic);
}
