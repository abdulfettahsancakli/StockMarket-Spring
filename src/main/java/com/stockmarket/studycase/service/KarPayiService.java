package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.KarPayi;

import java.util.List;
import java.util.Optional;

public interface KarPayiService {
    List<KarPayi> tumKarPaylariGetir();
    Optional<KarPayi> karPayiBulById(Long id);
    KarPayi karPayiKaydet(KarPayi karPayi);
    KarPayi karPayiGuncelle(KarPayi karPayi);
    void karPayiSil(Long id);
}