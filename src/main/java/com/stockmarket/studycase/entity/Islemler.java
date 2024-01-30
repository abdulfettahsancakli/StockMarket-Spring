package com.stockmarket.studycase.entity;
import com.stockmarket.studycase.enums.IslemTipi;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Islemler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
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