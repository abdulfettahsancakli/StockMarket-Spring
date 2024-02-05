package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.enums.KuponTuru;

import java.util.List;

public interface KuponService {
    List<Kupon> kuponOlustur(HisseSenedi hisseSenedi);

    List<Kupon> getKuponlarByHisseSenediId(Long hisseSenediId);

    void kuponKullan(Long kuponId);

    void addKuponList(List<Kupon> kuponList);

    void updateKuponList(List<Kupon> kuponList);

    List<Kupon> searchPayAlmaKuponuByTertip(Tertip tertip);

}
