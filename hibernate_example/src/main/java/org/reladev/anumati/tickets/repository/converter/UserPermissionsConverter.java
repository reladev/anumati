package org.reladev.anumati.tickets.repository.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.reladev.anumati.UserPermissions;

@Converter(autoApply = true)
public class UserPermissionsConverter implements AttributeConverter<UserPermissions, String> {

    @Override
    public String convertToDatabaseColumn(UserPermissions entityValue) {
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
    public UserPermissions convertToEntityAttribute(String databaseValue) {
        if (databaseValue == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            return mapper.readValue(databaseValue, UserPermissions.class);
        } catch (Exception e) {
            throw new RuntimeException("bad data");
        }
    }
}