package de.district.core.economy.converter;

import de.district.api.economy.bank.Bic;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * The {@code BicConverter} class implements the {@link AttributeConverter} interface to convert
 * {@link Bic} objects to their string representation for storage in the database and back to
 * {@code Bic} objects when retrieving from the database.
 *
 * <p>This class is annotated with {@link Converter}, making it a JPA converter that automatically
 * handles the conversion between {@code Bic} entities and the database column of type {@code String}.</p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
@Converter
public class BicConverter implements AttributeConverter<Bic, String> {

    /**
     * Converts the {@link Bic} entity attribute into its string representation
     * for storage in the database.
     *
     * @param attribute the {@code Bic} entity attribute to be converted
     * @return the string representation of the {@code Bic} to be stored in the database column
     */
    @Override
    public String convertToDatabaseColumn(final Bic attribute) {
        return attribute.toString();
    }

    /**
     * Converts the string stored in the database column into a {@link Bic} entity attribute.
     *
     * <p>It is the responsibility of the converter to ensure that the correct data type
     * is used for the database column.</p>
     *
     * @param dbData the string data from the database column
     * @return the corresponding {@link Bic} entity attribute
     */
    @Override
    public Bic convertToEntityAttribute(final String dbData) {
        return new Bic(dbData);
    }
}
