package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.repository.KuponRepository;
import com.stockmarket.studycase.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class KuponServiceImpl implements KuponService {

    @Autowired
    private KuponRepository kuponRepository;

    @Override
    public List<Kupon> kuponOlustur(HisseSenedi hisseSenedi) {
        List<Kupon> kuponList = new ArrayList<>();

        for (int i = 1; i <= 26; i++) {
            Kupon kupon = new Kupon();
            kupon.setUsed(false);
            kupon.setYil(2024);
            kupon.setKuponTuru((i <= 16) ? KuponTuru.PAY_ALMA : KuponTuru.KAR_PAYI);
            kupon.setKupon_no(i);
            kupon.setKupur_no((i <= 16) ? i : null);
            kupon.setHisseSenedi(hisseSenedi);
            kuponList.add(kupon);
        }

        return kuponRepository.saveAll(kuponList);
    }

    @Override
    public List<Kupon> getKuponlarByHisseSenediId(Long hisseSenediId) {
        return kuponRepository.findByHisseSenediId(hisseSenediId);
    }
}
