package org.reladev.anumati.tickets.repository.converter;

import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter(autoApply = true)
public class StringSetConverter implements AttributeConverter<Set<String>, String> {
    private static TypeReference<Set<String>> type = new TypeReference<Set<String>>() {};

    @Override
    public String convertToDatabaseColumn(Set<String> entityValue) {
        if (entityValue == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.writeValueAsString(entityValue);
        } catch (Exception e) {
            throw new RuntimeException("bad data");
        }
    }

    @Override
    public Set<String> convertToEntityAttribute(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(databaseValue, type);
        } catch (Exception e) {
            throw new RuntimeException("bad data");
        }
    }
}