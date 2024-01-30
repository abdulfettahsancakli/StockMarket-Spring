package com.stockmarket.studycase.enums.converters;
import com.stockmarket.studycase.enums.YatirimciTipi;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class YatirimciTipiConverter implements AttributeConverter<YatirimciTipi, Integer> {

    @Override
    public Integer convertToDatabaseColumn(YatirimciTipi yatirimciTipi) {
        if (yatirimciTipi == null) {
            return null;
        }
        return yatirimciTipi.getValue();
    }

    @Override
    public YatirimciTipi convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(YatirimciTipi.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
