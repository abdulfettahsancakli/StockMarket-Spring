package com.stockmarket.studycase.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
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

    // Boş constructor
    public HisseSenedi() {
    }
    @Builder
    public HisseSenedi(Long id, Integer seriNumarasi, Double nominalDeger, Boolean hisseVerildiMi,
                       List<Kupon> kuponList, Hissedarlar hissedar, Tertip tertip) {
        this.id = id;
        this.seriNumarasi = seriNumarasi;
        this.nominalDeger = nominalDeger;
        this.hisseVerildiMi = hisseVerildiMi;
        this.kuponList = kuponList;
        this.hissedar = hissedar;
        this.tertip = tertip;
    }
    @Override
    public String toString() {
        return "HisseSenedi{" +
                "id=" + id +
                ", seriNumarasi=" + seriNumarasi +
                ", nominalDeger=" + nominalDeger +
                ", hisseVerildiMi=" + hisseVerildiMi +
                '}';
    }

}
