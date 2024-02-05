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

        // Yeni tertip oluştur veya mevcut tertibi getir
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

/*
     @Override
     public SermayeArtisi SermayeArtisiOlustur(Double bedelliArtis, Double bedelsizArtis, Double artisOrani, String tertipNo) {
        Tertip tertip = tertipService.yeniTertipOlustur(tertipNo);
        Double eskiSermaye = 0.0; // Eski sermaye değerini belirle
        Double yeniSermaye = eskiSermaye + bedelliArtis + bedelsizArtis;
        SermayeArtisi sermayeArtisi = new SermayeArtisi();
        sermayeArtisi.setBedelliArtis(bedelliArtis);
        sermayeArtisi.setBedelsizArtis(bedelsizArtis);
        sermayeArtisi.setArtisOrani(artisOrani);
        sermayeArtisi.setAnlikSermaye(yeniSermaye);
        sermayeArtisi.setTertip(tertip);
        return sermayeArtisiRepository.save(sermayeArtisi);
    }
*/
    //Override
    //public Double eskiSermayeOgren(Long tertipId) {
    //   // Veritabanından daha önce kaydedilmiş sermaye artışlarını getir
    //   List<SermayeArtisi> gecmisSermayeArtislari = sermayeArtisiRepository.findByTertipId(tertipId);
    //
    //   System.out.println(gecmisSermayeArtislari);
    //
    //   // Eğer daha önce sermaye artışı yapılmışsa, en sonuncusunun anlık sermaye değerini al
    //   if (!gecmisSermayeArtislari.isEmpty()) {
    //       SermayeArtisi sonSermayeArtisi = gecmisSermayeArtislari.get(gecmisSermayeArtislari.size() - 1);
    //       return sonSermayeArtisi.getAnlıkSermaye();
    //   }
    //
    //   // Eğer daha önce sermaye artışı yapılmamışsa, varsayılan olarak 0.0 dön
    //   return 0.0;
    //


}
