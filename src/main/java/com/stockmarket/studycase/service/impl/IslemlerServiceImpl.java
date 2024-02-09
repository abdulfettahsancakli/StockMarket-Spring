package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Islemler;
import com.stockmarket.studycase.models.HisseSenediAddModel;
import com.stockmarket.studycase.models.KarPayiDağitModel;
import com.stockmarket.studycase.models.KarpayiIcinSenetAramaModel;
import com.stockmarket.studycase.repository.IslemlerRepository;
import com.stockmarket.studycase.service.*;
import com.stockmarket.studycase.specifications.IslemlerSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class IslemlerServiceImpl implements IslemlerService {


    private final IslemlerRepository islemlerRepository;
    private final TertipService tertipService;
    private final HisseSenediService hisseSenediService;
    private final KuponService kuponService;
    private final HissedarlarService hissedarlarService;
    private final SermayeArtisiService sermayeArtisiService;
    private final KarPayiService karPayiService;
    private final IslemlerSpecification islemlerSpecification;


    public IslemlerServiceImpl(@Autowired IslemlerRepository islemlerRepository,
                               @Autowired TertipService tertipService,
                               @Autowired HisseSenediService hisseSenediService,
                               @Autowired KuponService kuponService,
                               @Autowired HissedarlarService hissedarlarService,
                               @Autowired SermayeArtisiService sermayeArtisiService,
                               @Autowired KarPayiService karPayiService,
                               @Autowired IslemlerSpecification islemlerSpecification) {
        this.islemlerRepository = islemlerRepository;
        this.tertipService = tertipService;
        this.hisseSenediService = hisseSenediService;
        this.kuponService = kuponService;
        this.hissedarlarService = hissedarlarService;
        this.sermayeArtisiService = sermayeArtisiService;
        this.karPayiService = karPayiService;
        this.islemlerSpecification = islemlerSpecification;
    }

    @Override
    public List<HisseSenedi> hisseSenediAra(HisseSenediAddModel hisseSenediAddModel) {
        return null;
    }

    @Override
    public void hisseSenediVer(List<HisseSenediAddModel> hisseSenediAddModelList) {

    }

    @Override
    public List<HisseSenedi> karPayıIcinUygunHisseSenediListele(KarpayiIcinSenetAramaModel karpayiIcinSenetAramaModel) {
        return null;
    }

    @Override
    public void karPayiVer(List<KarPayiDağitModel> listKarPayiDagitModel) {

    }

    @Override
    public Islemler findIslemById(String id) {
        return null;
    }
}
