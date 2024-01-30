package com.stockmarket.studycase.entity;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.enums.converters.KuponTuruConverter;
import com.stockmarket.studycase.enums.converters.YatirimciTipiConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Kupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isUsed;
    private String kupon_no;
    private String kupur_no;
    private int yil;

    @Convert(converter = KuponTuruConverter.class)
    private KuponTuru kuponTuru;

    @ManyToOne
    @JoinColumn(name = "hisse_senedi_id")
    private HisseSenedi hisseSenedi;

    @OneToMany(mappedBy = "kupon")
    private List<Islemler> islemlerList;
}
