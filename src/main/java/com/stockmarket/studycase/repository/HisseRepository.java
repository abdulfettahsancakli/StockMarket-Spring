package com.stockmarket.studycase.repository;


import com.stockmarket.studycase.entity.HisseSenedi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HisseRepository
        extends JpaRepository<HisseSenedi, Long> {
}