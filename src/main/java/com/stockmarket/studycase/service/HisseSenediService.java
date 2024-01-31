package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;

import java.util.List;

public interface HisseSenediService {
    HisseSenedi HisseSenediOluştur(Long hissedarId, Long tertipId, Double nominalDeger);
    void hisseSenediVer(Long hisseSenediId, Long hissedarId);
    List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId);
}
