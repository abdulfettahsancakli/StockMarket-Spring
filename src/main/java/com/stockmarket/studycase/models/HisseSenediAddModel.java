package com.stockmarket.studycase.models;
import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class HisseSenediAddModel {

    @Nonnull
    private Long baslangicSeriNumarasi;

    @Nonnull
    private Long bitisSeriNumarasi;

    @Nonnull
    private String hissedarId;

    @Nonnull
    private String tertipId;
}
