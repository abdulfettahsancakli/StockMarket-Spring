package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.models.HisseSenediOlusturModel;

import java.util.List;

public interface HisseSenediService {
    HisseSenedi HisseSenediOlustur(Long tertipId, Double nominalDeger);
    void hisseSenediVer(Long hisseSenediId, Long hissedarId);
    List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId);
    HisseSenedi SenetOlustur(List<HisseSenediOlusturModel> senetBilgileri, Long tertipId);
}
