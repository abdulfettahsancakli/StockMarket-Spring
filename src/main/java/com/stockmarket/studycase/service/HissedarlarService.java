package com.stockmarket.studycase.service;


import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.models.HissedarAddModel;
import com.stockmarket.studycase.models.HissedarSearchModel;

import java.util.List;
import java.util.Optional;

public interface HissedarlarService {

    Optional<Hissedarlar> HissedarBul_Id(Long id);

    void hissedarSil(Long id);

    List<Hissedarlar> listHissedarlar();

    Hissedarlar hissedarEkle(HissedarAddModel hissedarAddModel);

    List<Hissedarlar> listeleHissedarlar();

    List<Hissedarlar> listHissedarlarBySearch(HissedarSearchModel ortakSearchModel);

    Boolean checkUniqueSicil(String sicil);
}