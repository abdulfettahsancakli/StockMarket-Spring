package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.SermayeArtisi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermayeArtisiRepository extends JpaRepository<SermayeArtisi, Long> {
   // List<SermayeArtisi> findByTertipId(Long tertipId);

}
