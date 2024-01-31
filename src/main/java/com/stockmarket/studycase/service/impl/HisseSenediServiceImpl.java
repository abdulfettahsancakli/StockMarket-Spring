package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.repository.HisseSenediRepository;
import com.stockmarket.studycase.service.HisseSenediService;
import com.stockmarket.studycase.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HisseSenediServiceImpl implements HisseSenediService {
    @Autowired
    private HisseSenediRepository hisseSenediRepository;

    @Autowired
    private KuponService kuponService;

    @Override
    public HisseSenedi HisseSenediOluştur(Long hissedarId, Long tertipId, Double nominalDeger) {

        HisseSenedi hisseSenedi = new HisseSenedi();
        hisseSenedi.setHissedarId(hissedarId);
        hisseSenedi.setTertipId(tertipId);
        hisseSenedi.setNominalDeger(nominalDeger);
        hisseSenedi.setHisseVerildiMi(false);

        // Hisse senedine bağlı kuponları oluştur
        List<Kupon> kuponlar = kuponService.kuponOlustur(hisseSenedi);
        hisseSenedi.setKuponList(kuponlar);

        return hisseSenediRepository.save(hisseSenedi);
    }
    @Override
    public void hisseSenediVer(Long hisseSenediId, Long hissedarId) {

    }

    @Override
    public List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId) {
        return kuponService.getKuponlarByHisseSenediId(hisseSenediId);
    }
}
