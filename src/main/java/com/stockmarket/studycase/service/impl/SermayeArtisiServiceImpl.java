package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.repository.SermayeArtisiRepository;
import com.stockmarket.studycase.service.SermayeArtisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SermayeArtisiServiceImpl implements SermayeArtisiService {

    @Autowired
    private SermayeArtisiRepository sermayeArtisiRepository;

    @Override
    public List<SermayeArtisi> tumSermayeArtislariniGetir() {
        return sermayeArtisiRepository.findAll();
    }

    @Override
    public Optional<SermayeArtisi> sermayeArtisiBulById(Long id) {
        return sermayeArtisiRepository.findById(id);
    }

    @Override
    public SermayeArtisi sermayeArtisiKaydet(SermayeArtisi sermayeArtisi) {
        return sermayeArtisiRepository.save(sermayeArtisi);
    }

    @Override
    public SermayeArtisi sermayeArtisiGuncelle(SermayeArtisi sermayeArtisi) {
        if (sermayeArtisiRepository.existsById(sermayeArtisi.getId())) {
            return sermayeArtisiRepository.save(sermayeArtisi);
        } else {
            return null;
        }
    }

    @Override
    public void sermayeArtisiSil(Long id) {
        sermayeArtisiRepository.deleteById(id);
    }
}
