package com.stockmarket.studycase.models;

import com.stockmarket.studycase.enums.YatirimciTipi;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class HissedarUpdateModel {

    @Nonnull
    private Long hissedar_id;

    @Nonnull
    private String unvan;

    @Nonnull
    private String adres;

    @Nonnull
    private String telefon;

    @Nonnull
    private YatirimciTipi yatirimciTipi;

    @Nonnull
    private String hissedarSicilNumarasi;

}
