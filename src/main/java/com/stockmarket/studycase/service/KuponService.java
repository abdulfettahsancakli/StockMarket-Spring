package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.enums.KuponTuru;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface KuponService {
    List<Kupon> kuponOlustur(HisseSenedi hisseSenedi);

    List<Kupon> getKuponlarByHisseSenediId(Long hisseSenediId);

    Specification<Kupon> searchPayAlmaKuponuBySenet(HisseSenedi hisseSenedi);

    void kuponKullan(Long kuponId);


}
