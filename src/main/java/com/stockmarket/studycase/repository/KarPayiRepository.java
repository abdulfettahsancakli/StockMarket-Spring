package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.KarPayi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface KarPayiRepository extends JpaRepository<KarPayi,Long> {
    int countByDagitimYili(Integer dagitimYili);

    @Query("SELECT COALESCE(MAX(k.yilIcindeSeri), 0) FROM KarPayi k WHERE k.dagitimYili = :dagitimYili")
    int findMaxSeriNumarasiByDagitimYili(@Param("dagitimYili") Integer dagitimYili);

}
