package com.stockmarket.studycase.entity;

import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.enums.converters.YatirimciTipiConverter;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
public class Hissedarlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unvan;
    private String adres;
    private String telefon;
    @Convert(converter = YatirimciTipiConverter.class)
    private YatirimciTipi yatirimciTipi;
    @Column(unique = true, length = 8)
    private String sicilNumarasi;

    public Hissedarlar() {

    }

    public Hissedarlar(Long id, String unvan, String adres, String telefon, YatirimciTipi yatirimciTipi, String sicilNumarasi, List<HisseSenedi> hisseler) {
        this.id = id;
        this.unvan = unvan;
        this.adres = adres;
        this.telefon = telefon;
        this.yatirimciTipi = yatirimciTipi;
        this.sicilNumarasi = sicilNumarasi;
        this.hisseler = hisseler;
    }

    @OneToMany(mappedBy = "hissedar")
    private List<HisseSenedi> hisseler;
}