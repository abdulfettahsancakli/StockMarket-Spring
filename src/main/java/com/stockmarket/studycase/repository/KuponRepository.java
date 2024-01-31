package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.Kupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KuponRepository extends JpaRepository<Kupon,Long> {
    List<Kupon> findByHisseSenediId(Long hisseSenediId);
}
