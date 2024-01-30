package com.stockmarket.studycase.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class SermayeArtisi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "bedelli_artis")
    private Double bedelliArtis;

    @Column(name = "bedelsiz_artis")
    private Double bedelsizArtis;

    @Column(name = "artis_orani")
    private Double artisOrani;

    @Column(name = "anlik_sermaye")
    private Double anlikSermaye;

    /* @OneToMany(mappedBy = "sermayeArtisi") private List<Hisse> hisseler;  */

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private Tertip tertip;


    // İlişki: Bir sermaye artışına ait birden çok kar payı dağıtımı
    // Bu liste, her bir sermaye artışına ait birden fazla kar payı dağıtımını temsil eder
    //@OneToMany(mappedBy = "sermayeArtisi", cascade = CascadeType.ALL)
    //private List<KarPayi> karPayilari;

    public Double getAnlıkSermaye() {
        return anlikSermaye + bedelliArtis + bedelsizArtis;
    }
}
