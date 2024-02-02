package com.stockmarket.studycase.service.impl;


import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.repository.TertipRepository;
import com.stockmarket.studycase.service.TertipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Year;
import java.util.UUID;

@Service
public class TertipServiceImpl implements TertipService {

    @Autowired
    private TertipRepository tertipRepository;

   /* @Override
    public Tertip yeniTertipOlustur() {
        Tertip yeniTertip = new Tertip();
        yeniTertip.setTertipNo(generateUniqueTertipNo());
        yeniTertip.setYil(calculateYear());
        return tertipRepository.save(yeniTertip);
    }
    */
   @Override
   public Tertip yeniTertipOlustur(String tertipNo) {
       Tertip yeniTertip = new Tertip();
       yeniTertip.setTertipNo(tertipNo);
       yeniTertip.setYil("2024");
       return tertipRepository.save(yeniTertip);
   }

    private String calculateYear() {
        return "2024";
    }

    private String generateUniqueTertipNo() {
        return "TERTIP_" + UUID.randomUUID().toString();
    }


}
