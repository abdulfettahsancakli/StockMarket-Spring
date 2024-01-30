package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.repository.HissedarRepository;
import com.stockmarket.studycase.service.HissedarlarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HissedarlarServiceImpl implements HissedarlarService {

    @Autowired
    private HissedarRepository hissedarRepository;

    @Override
    public Hissedarlar saveHissedar(Hissedarlar hissedar) {
        return hissedarRepository.save(hissedar);
    }

    @Override
    public Hissedarlar updateHissedar(Hissedarlar hissedar) {

        Hissedarlar existingHissedar = hissedarRepository.findById(hissedar.getId())
                .orElseThrow(() -> new RuntimeException("Hissedar bulunamadı"));

        existingHissedar.setUnvan(hissedar.getUnvan());
        existingHissedar.setAdres(hissedar.getAdres());
        existingHissedar.setTelefon(hissedar.getTelefon());
        existingHissedar.setYatirimciTipi(hissedar.getYatirimciTipi());
        existingHissedar.setSicilNumarasi(hissedar.getSicilNumarasi());

        return hissedarRepository.save(existingHissedar);
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


}
