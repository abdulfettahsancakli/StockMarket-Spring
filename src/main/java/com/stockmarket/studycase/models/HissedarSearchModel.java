package com.stockmarket.studycase.models;

import com.stockmarket.studycase.enums.YatirimciTipi;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HissedarSearchModel {


    private Long id;
    private String unvan;
    private String adres;
    private YatirimciTipi yatirimciTipi;
    private String telefon;
    private String hissedarSicilNumarasi;
}
