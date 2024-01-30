package com.stockmarket.studycase.service;


import com.stockmarket.studycase.entity.Hissedarlar;

import java.util.List;
import java.util.Optional;

public interface HissedarlarService {


    Hissedarlar saveHissedar(Hissedarlar hissedar);
    Hissedarlar updateHissedar(Hissedarlar hissedar);

    // Hissedarlar hissedarEkle(HissedarAddModel hissedarAddModel);
    // Hissedarlar hissedarGüncelle(HissedarUpdateModel hissedarUpdateModel);
    Optional<Hissedarlar> HissedarBul_Id(Long id);
    void hissedarSil(Long id);
    List<Hissedarlar> listHissedarlar();



}