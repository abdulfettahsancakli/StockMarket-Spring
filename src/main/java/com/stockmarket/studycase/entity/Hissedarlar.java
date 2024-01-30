package com.stockmarket.studycase.entity;

import com.stockmarket.studycase.enums.YatirimciTipi;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Hissedarlar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unvan;
    private String adres;
    private String telefon;
    @Enumerated(EnumType.STRING)
    private YatirimciTipi yatirimciTipi;
    @Column(unique = true, length = 8)
    private String sicilNumarasi;

    @OneToMany(mappedBy = "hissedar")
    private List<HisseSenedi> hisseler;
}