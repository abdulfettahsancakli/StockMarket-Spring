package com.stockmarket.studycase.repository;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Tertip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



public interface TertipRepository extends JpaRepository<Tertip,Long> {


}
