package com.codeup.crudspring.enums.converters;

import com.codeup.crudspring.enums.StatusEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class StatusEnumConverter implements AttributeConverter<StatusEnum, String> {

    @Override
    public String convertToDatabaseColumn(StatusEnum status) {
        if (status == null) {
            return null;
        }
        return status.getValue();
    }

    @Override
    public StatusEnum convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return Stream.of(StatusEnum.values())
                .filter(c -> c.getValue().equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}