package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.repository.SermayeArtisiRepository;
import com.stockmarket.studycase.service.SermayeArtisiService;
import com.stockmarket.studycase.service.TertipService;
import com.stockmarket.studycase.specifications.SermayeArtisiSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SermayeArtisiServiceImpl implements SermayeArtisiService {

    private final SermayeArtisiRepository sermayeArtisiRepository;
    private SermayeArtisiSpecification sermayeArtisiSpecification;

    @Autowired
    private TertipService tertipService;

    @Autowired
    public SermayeArtisiServiceImpl( @Autowired SermayeArtisiRepository sermayeArtisiRepository,
                                     @Autowired SermayeArtisiSpecification sermayeArtisiSpecification)
    {
        this.sermayeArtisiRepository = sermayeArtisiRepository;
        this.sermayeArtisiSpecification = sermayeArtisiSpecification;
    }


    @Override
    public List<SermayeArtisi> TumSermayeArtislari() {
        return sermayeArtisiRepository.findAll();
    }

    @Override
    public SermayeArtisi IdyeGoreSermayeArtisi(Long id) {
        return sermayeArtisiRepository.findById(id).orElse(null);
    }

    @Override
    public SermayeArtisi SermayeArtisiOlustur(Double bedelliArtis, Double bedelsizArtis, Double artisOrani, Double eskiSermaye) {

        // Yeni tertip oluştur
        Tertip tertip = tertipService.yeniTertipOlustur();

        // Yeni sermaye artısı nesnesini oluştur ve kaydet
        SermayeArtisi sermayeArtisi = new SermayeArtisi();
        sermayeArtisi.setBedelliArtis(bedelliArtis);
        sermayeArtisi.setBedelsizArtis(bedelsizArtis);
        sermayeArtisi.setArtisOrani(artisOrani);
        Double yeniSermaye = eskiSermaye + bedelliArtis + bedelsizArtis;
        sermayeArtisi.setAnlikSermaye(yeniSermaye);
        sermayeArtisi.setTertip(tertip);

        return sermayeArtisiRepository.save(sermayeArtisi);
    }


    public List<SermayeArtisi> searchSermayeArtisiByTertip(String tertipNo) {
        Specification<SermayeArtisi> spec = sermayeArtisiSpecification.searchSermayeArtisiByTertip(tertipNo);
        return sermayeArtisiRepository.findAll(spec);
    }

}
