package com.stockmarket.studycase.repository;


import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Tertip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HisseSenediRepository extends JpaRepository<HisseSenedi, Long> {

    //@Query("SELECT hs FROM HisseSenedi hs WHERE hs.tertip.id = :tertipId ORDER BY hs.seriNumarasi ASC")
    //HisseSenedi findFirstSenetByTertip(@Param("tertipId") Long tertipId);

    @Query("SELECT MAX(hs.seriNumarasi) FROM HisseSenedi hs WHERE hs.tertip.id = :tertipId")
    Integer findMaxSeriNumarasiByTertipId(@Param("tertipId") Long tertipId);

}

