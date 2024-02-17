package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.models.HisseSenediOlusturModel;

import java.util.List;
import java.util.Optional;

public interface HisseSenediService {

    void hisseSenediVer(Long hisseSenediId, Long hissedarId);
    List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId);
    HisseSenedi HisseSenediOlustur(List<HisseSenediOlusturModel> senetBilgileri, Long tertipId);

}
