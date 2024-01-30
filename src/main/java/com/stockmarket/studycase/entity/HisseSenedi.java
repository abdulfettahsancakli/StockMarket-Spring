package com.stockmarket.studycase.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class HisseSenedi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer seriNumarasi;
    private Double nominalDeger;
    private Boolean hisseVerildiMi;

    @OneToMany(mappedBy = "hisseSenedi", cascade = CascadeType.ALL)
    private List<Kupon> kuponList;

    @ManyToOne
    @JoinColumn(name = "hissedar_id")
    private Hissedarlar hissedar;

    @ManyToOne
    @JoinColumn(name = "tertip_id")
    private Tertip tertip;
}
