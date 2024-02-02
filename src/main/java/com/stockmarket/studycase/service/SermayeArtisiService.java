package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.SermayeArtisi;

import java.util.List;
import java.util.Optional;

public interface SermayeArtisiService {
    List<SermayeArtisi> TumSermayeArtislari();

    SermayeArtisi IdyeGoreSermayeArtisi(Long id);

    SermayeArtisi SermayeArtisiOlustur(Double bedelliArtis, Double bedelsizArtis, Double artisOrani, String tertipNo);

    //Double eskiSermayeOgren(Long tertipId);

}