package com.stockmarket.studycase.enums.converters;

import com.stockmarket.studycase.enums.KuponTuru;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class KuponTuruConverter implements AttributeConverter<KuponTuru, Integer> {

    @Override
    public Integer convertToDatabaseColumn(KuponTuru kuponTuru) {
        if (kuponTuru == null) {
            return null;
        }
        return kuponTuru.getValue();
    }

    @Override
    public KuponTuru convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Stream.of(KuponTuru.values())
                .filter(c -> c.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
