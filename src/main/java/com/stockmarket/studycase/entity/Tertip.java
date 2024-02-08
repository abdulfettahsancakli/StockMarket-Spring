package com.stockmarket.studycase.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Tertip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tertip_id;
    private String tertipNo;
    @Column(name = "yil")
    private String yil;

    // Her tertibe ait senetleri takip etmek için OneToMany ilişkisi
    @OneToMany(mappedBy = "tertip", cascade = CascadeType.ALL)
    private List<HisseSenedi> hisseSenediList;


    @Override
    public String toString() {
        return "Tertip{" +
                "tertip_id=" + tertip_id +
                ", tertipNo='" + tertipNo + '\'' +
                ", yil='" + yil + '\'' +
                '}';
    }
}
