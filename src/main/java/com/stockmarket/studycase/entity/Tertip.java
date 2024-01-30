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
    private int yil;

}
