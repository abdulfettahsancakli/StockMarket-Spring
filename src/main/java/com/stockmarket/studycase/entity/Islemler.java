package com.stockmarket.studycase.entity;
import com.stockmarket.studycase.enums.IslemTipi;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.enums.converters.IslemTipiConverter;
import com.stockmarket.studycase.enums.converters.KuponTuruConverter;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Islemler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = IslemTipiConverter.class)
    private IslemTipi islemTipi;

    @ManyToOne
    @JoinColumn(name = "kupon_id")
    private Kupon kupon;

    private LocalDateTime islem_zamani;

    @ManyToOne
    @JoinColumn(name = "karpayi_donemi")
    private KarPayi karpayiDonemi;

    private double karpayi_tutari;
}