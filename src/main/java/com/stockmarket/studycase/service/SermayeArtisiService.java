package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.SermayeArtisi;

import java.util.List;
import java.util.Optional;

public interface SermayeArtisiService {
    List<SermayeArtisi> tumSermayeArtislariniGetir();
    Optional<SermayeArtisi> sermayeArtisiBulById(Long id);
    SermayeArtisi sermayeArtisiKaydet(SermayeArtisi sermayeArtisi);
    SermayeArtisi sermayeArtisiGuncelle(SermayeArtisi sermayeArtisi);
    void sermayeArtisiSil(Long id);
}