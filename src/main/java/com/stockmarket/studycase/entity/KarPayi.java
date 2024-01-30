package com.stockmarket.studycase.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class KarPayi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double dagitimOrani;
    private Integer dagitimYili;
    private Integer yilIcindeSeri;

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private Tertip tertip;

    //@ManyToOne
    //@JoinColumn(name = "sermaye_artisi_id")
    //private SermayeArtisi sermayeArtisi;
}
