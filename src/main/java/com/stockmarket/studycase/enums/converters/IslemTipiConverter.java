package com.stockmarket.studycase.enums.converters;

import com.stockmarket.studycase.enums.IslemTipi;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class IslemTipiConverter implements AttributeConverter<IslemTipi, Integer> {

    @Override
    public Integer convertToDatabaseColumn(IslemTipi islemTipi) {
        if (islemTipi == null) {
            return null;
        }
        return islemTipi.getValue();
    }

    @Override
    public IslemTipi convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(IslemTipi.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
