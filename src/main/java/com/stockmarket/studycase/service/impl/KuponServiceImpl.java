package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.repository.KuponRepository;
import com.stockmarket.studycase.service.KuponService;
import com.stockmarket.studycase.specifications.KuponSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KuponServiceImpl implements KuponService {


    private final KuponRepository kuponRepository;
    private final KuponSpecification kuponSpecification;

    public KuponServiceImpl(@Autowired KuponRepository kuponRepository,
                            @Autowired KuponSpecification kuponSpecification) {
        this.kuponRepository = kuponRepository;
        this.kuponSpecification = kuponSpecification;
    }

    @Override
    public List<Kupon> kuponOlustur(HisseSenedi hisseSenedi) {
        List<Kupon> kuponList = new ArrayList<>();

        // Tertip yılını ve kar payı kuponlarının başlangıç yılını al
        int baslangicYili = Integer.parseInt(hisseSenedi.getTertip().getYil());

        for (int i = 1; i <= 26; i++) {
            Kupon kupon = new Kupon();
            kupon.setUsed(false);
            kupon.setHisseSenedi(hisseSenedi);
            kupon.setKupon_no(i);
            kupon.setKuponTuru((i <= 16) ? KuponTuru.PAY_ALMA : KuponTuru.KAR_PAYI);
            kupon.setKupur_no((i <= 16) ? i : null);
            if (i <= 16) {
                kupon.setKupur_no(i);
                // Küpür numarası olan kuponların yılını tertip yılı olarak ayarladan null olarak değiştir
                //kupon.setYil(null);
            } else {
                // Kar payı kuponları için yıl değerlerini artarak belirle
                kupon.setYil(baslangicYili++);
            }

            kuponList.add(kupon);
        }

        return kuponRepository.saveAll(kuponList);
    }

    @Override
    public List<Kupon> getKuponlarByHisseSenediId(Long hisseSenediId) {
        return kuponRepository.findByHisseSenediId(hisseSenediId);
    }

    @Override
    public void kuponKullan(Long kuponId) {
        Optional<Kupon> optionalKupon = kuponRepository.findById(kuponId);

        if (optionalKupon.isPresent()) {
            Kupon kupon = optionalKupon.get();

            if (kupon.isUsed()) {
                throw new RuntimeException("Kupon zaten kullanılmış");
            }

            kupon.setUsed(true);
            kuponRepository.save(kupon);
        } else {
            throw new RuntimeException("Kupon bulunamadı");
        }
    }


    @Override
    public Specification<Kupon> searchPayAlmaKuponuBySenet(HisseSenedi hisseSenedi) {
        return searchPayAlmaKuponuBySenet(hisseSenedi);
    }

    @Override
    public void addKuponList(List<Kupon> kuponList) {
        kuponRepository.saveAll(kuponList);
    }

    // @Override
    // public void updateKuponList(List<Kupon> kuponList) {
    //    kuponRepository.updateAll(kuponList);
    // }

    //@Override
    //public List<Kupon> searchPayAlmaKuponuByTertip(Tertip tertip) {
    //    return kuponRepository.findAll(kuponSpecification.searchPayAlmaKuponuByTertipNo(tertip.getTertipNo()));
    //}


}
