package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.exception.UniqueSicilException;
import com.stockmarket.studycase.models.HissedarAddModel;
import com.stockmarket.studycase.models.HissedarSearchModel;
import com.stockmarket.studycase.repository.HissedarRepository;
import com.stockmarket.studycase.service.HissedarlarService;
import com.stockmarket.studycase.specifications.HissedarlarSpecification;
import com.stockmarket.studycase.util.StringUtils;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service

public class HissedarlarServiceImpl implements HissedarlarService {


    private final HissedarRepository hissedarRepository;
    private final HissedarlarSpecification hissedarlarSpecification;

    public HissedarlarServiceImpl(@Autowired HissedarRepository hissedarRepository,
                                  @Autowired HissedarlarSpecification hissedarlarSpecification) {
        this.hissedarRepository = hissedarRepository;
        this.hissedarlarSpecification = hissedarlarSpecification;
    }


    @Override
    public Optional<Hissedarlar> HissedarBul_Id(Long id) {
        return hissedarRepository.findById(id);
    }

    @Override
    public void hissedarSil(Long id) {
        hissedarRepository.deleteById(id);
    }

    @Override
    public List<Hissedarlar> listHissedarlar() {
        return hissedarRepository.findAll();
    }

    @Override
    public Hissedarlar hissedarEkle(HissedarAddModel hissedarAddModel) {
        if(checkUniqueSicil(hissedarAddModel.getHissedarSicilNumarasi())){
            throw new UniqueSicilException("Bu sicil numarası ile kayıt mevcuttur");
         }
        Hissedarlar hissedar = Hissedarlar.builder()
                .unvan(StringUtils.toUpperCaseTurkish(hissedarAddModel.getUnvan()))
                .adres(StringUtils.toUpperCaseTurkish(hissedarAddModel.getAdres()))
                .telefon(hissedarAddModel.getTelefon())
                .yatirimciTipi(YatirimciTipi.valueOf(String.valueOf(hissedarAddModel.getYatirimciTipi())))
                .sicilNumarasi(hissedarAddModel.getHissedarSicilNumarasi())
                .build();

        return hissedarRepository.save(hissedar);
    }

    @Override
    public List<Hissedarlar> listeleHissedarlar() {
        return hissedarRepository.findAllByOrderByUnvanAsc();
    }

    @Override
    public List<Hissedarlar> listHissedarlarBySearch(HissedarSearchModel hissedarSearchModel) {
        return hissedarRepository.findAll(hissedarlarSpecification.searchHissedarlarBySearchModel(hissedarSearchModel));
    }

    @Override
    public Boolean checkUniqueSicil(String sicil) {
        return hissedarRepository.findBysicilNumarasi(sicil)!=null;
    }


}
