package de.district.core.economy.converter;

import de.district.api.economy.bank.Iban;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * The {@code IbanConverter} class implements the {@link AttributeConverter} interface to convert
 * {@link Iban} objects to their string representation for storage in the database and back to
 * {@code Iban} objects when retrieving from the database.
 *
 * <p>This class is annotated with {@link Converter}, making it a JPA converter that automatically
 * handles the conversion between {@code Iban} entities and the database column of type {@code String}.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Converter
public class IbanConverter implements AttributeConverter<Iban, String> {

    /**
     * Converts the {@link Iban} entity attribute into its string representation
     * for storage in the database.
     *
     * @param attribute the {@code Iban} entity attribute to be converted
     * @return the string representation of the {@code Iban} to be stored in the database column
     */
    @Override
    public String convertToDatabaseColumn(final Iban attribute) {
        return attribute.toString();
    }

    /**
     * Converts the string stored in the database column into a {@link Iban} entity attribute.
     *
     * <p>It is the responsibility of the converter to ensure that the correct data type
     * is used for the database column.</p>
     *
     * @param dbData the string data from the database column
     * @return the corresponding {@link Iban} entity attribute
     */
    @Override
    public Iban convertToEntityAttribute(final String dbData) {
        return Iban.fromDistrictString(dbData);
    }
}
