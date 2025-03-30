package de.district.core.character.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;

/**
 * The {@code LocalDateTimeAttributeConverter} class is an implementation of the {@link AttributeConverter}
 * interface that converts {@link LocalDateTime} objects to {@link String} values for storage in the database
 * and vice versa. This class is used to persist {@code LocalDateTime} values in JPA entities.
 *
 * <p>The class is annotated with {@link Converter} to indicate that it is a JPA converter that can be used
 * to convert {@code LocalDateTime} attributes in entities to database columns and vice versa.</p>
 *
 * @see AttributeConverter
 * @see Converter
 * @since 1.0.0
 */
@Converter
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, String> {

    /**
     * Converts the specified {@link LocalDateTime} attribute to a {@link String} value for storage in the database.
     *
     * @param attribute the {@code LocalDateTime} attribute to convert.
     * @return the {@code String} representation of the {@code LocalDateTime} attribute.
     */
    @Override
    public String convertToDatabaseColumn(final LocalDateTime attribute) {
        return attribute.toString();
    }

    /**
     * Converts the specified {@link String} value from the database to a {@link LocalDateTime} attribute.
     *
     * @param dbData the {@code String} value from the database to convert.
     * @return the {@code LocalDateTime} attribute represented by the {@code String} value.
     */
    @Override
    public LocalDateTime convertToEntityAttribute(final String dbData) {
        return LocalDateTime.parse(dbData);
    }
}
