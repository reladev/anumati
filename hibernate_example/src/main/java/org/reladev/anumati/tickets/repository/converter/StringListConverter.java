package org.reladev.anumati.tickets.repository.converter;

import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Converter(autoApply = true)
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static TypeReference<List<String>> type = new TypeReference<List<String>>() {};

    @Override
    public String convertToDatabaseColumn(List<String> entityValue) {
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
    public List<String> convertToEntityAttribute(String databaseValue) {
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