package com.stockmarket.studycase.service.impl;


import com.stockmarket.studycase.entity.KarPayi;
import com.stockmarket.studycase.repository.KarPayiRepository;
import com.stockmarket.studycase.service.KarPayiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KarPayiServiceImpl implements KarPayiService {

    @Autowired
    private KarPayiRepository karPayiRepository;


    @Override
    public List<KarPayi> tumKarPaylariGetir() {
        return karPayiRepository.findAll();
    }

    @Override
    public Optional<KarPayi> karPayiBulById(Long id) {
        return karPayiRepository.findById(id);
    }

    @Override
    public KarPayi karPayiKaydet(KarPayi karPayi) {
        return karPayiRepository.save(karPayi);
    }

    @Override
    public KarPayi karPayiGuncelle(KarPayi karPayi) {
        if (karPayiRepository.existsById(karPayi.getId())) {
            return karPayiRepository.save(karPayi);
        } else {
            return null;
        }
    }

    @Override
    public void karPayiSil(Long id) {
        karPayiRepository.deleteById(id);
    }
}
