package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.entity.Tertip;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SermayeArtisiRepository extends JpaRepository<SermayeArtisi, Long> ,JpaSpecificationExecutor<SermayeArtisi>{
        //List<SermayeArtisi> findByTertipId(Tertip tertipId);
        List<SermayeArtisi> searchSermayeArtisiByTertip(Specification<SermayeArtisi> specification);
        //List<SermayeArtisi> findByTertipId(Long tertipId);
}
