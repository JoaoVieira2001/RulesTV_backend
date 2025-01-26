package com.RulesTV.RulesTV.configs;

import com.RulesTV.RulesTV.entity.UserPreferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;

import java.io.IOException;

@Converter(autoApply = true)
public class UserPreferencesConverter implements AttributeConverter<UserPreferences,String> {

    private final ObjectMapper objectMapper;

    public UserPreferencesConverter() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String convertToDatabaseColumn(UserPreferences userPreferences){
        try {
            return objectMapper.writeValueAsString(userPreferences);
        }catch (IOException e){
            throw new IllegalArgumentException("Could not convert UserPreferences to JSON", e);
        }
    }

    @Override
    public UserPreferences convertToEntityAttribute(String dbData){
        try{
            return objectMapper.readValue(dbData,UserPreferences.class);
        } catch (IOException e){
            throw new IllegalArgumentException("Could not convert JSON to UserPreferences", e);
        }
    }
}
