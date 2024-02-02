package com.stockmarket.studycase.service;


import com.stockmarket.studycase.entity.Hissedarlar;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface HissedarlarService {
    Hissedarlar saveHissedar(Hissedarlar hissedar);
    Hissedarlar updateHissedar(Hissedarlar hissedar);
    Optional<Hissedarlar> HissedarBul_Id(Long id);
    void hissedarSil(Long id);
    List<Hissedarlar> listHissedarlar();

    List<Hissedarlar> findByCriteria(Specification<Hissedarlar> specification);




}