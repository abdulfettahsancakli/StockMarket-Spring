package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.Kupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KuponRepository extends JpaRepository<Kupon,Long> {
}
