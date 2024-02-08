package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.KarPayi;
import com.stockmarket.studycase.models.KarPayiRequest;

import java.util.List;
import java.util.Optional;

public interface KarPayiService {


    void karPayiDagit(Long tertipId, Double dagitimOrani, Integer dagitimYili);

    List<KarPayi> tumKarPaylariGetir();
    Optional<KarPayi> karPayiBulById(Long id);
    KarPayi karPayiKaydet(KarPayi karPayi);
    KarPayi karPayiGuncelle(KarPayi karPayi);
    void karPayiSil(Long id);
}