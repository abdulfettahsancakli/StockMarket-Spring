package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.Hissedarlar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HissedarRepository extends JpaRepository<Hissedarlar, Long>,JpaSpecificationExecutor<Hissedarlar> {
    List<Hissedarlar> findAllByOrderByUnvanAsc();
    Hissedarlar findBysicilNumarasi(String sicilNumarasi);
}
