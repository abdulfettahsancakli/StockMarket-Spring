package com.stockmarket.studycase.service;

import com.stockmarket.studycase.entity.HisseSenedi;

import java.util.List;
import java.util.Optional;

public interface HisseService {
    List<HisseSenedi> tumHisseleriGetir();
    Optional<HisseSenedi> hisseBulById(Long id);
    HisseSenedi hisseKaydet(HisseSenedi hisse);
    HisseSenedi hisseGuncelle(HisseSenedi hisse);
    void hisseSil(Long id);
}
