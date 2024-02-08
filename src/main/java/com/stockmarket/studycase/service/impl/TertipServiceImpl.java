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

    private static int tertipCounter = 0;

    public Tertip yeniTertipOlustur() {
        String yeniTertipNo = generateTertipNo();
        Tertip yeniTertip = new Tertip();
        yeniTertip.setYil(String.valueOf(getCurrentYear()));
        yeniTertip.setTertipNo(yeniTertipNo);

        return tertipRepository.save(yeniTertip);
    }

    private String generateTertipNo() {
        return "T" + (++tertipCounter);
    }

    private int getCurrentYear() {
        return java.time.Year.now().getValue();
    }

}