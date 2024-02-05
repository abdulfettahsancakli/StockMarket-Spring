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

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private Tertip tertip;

}
