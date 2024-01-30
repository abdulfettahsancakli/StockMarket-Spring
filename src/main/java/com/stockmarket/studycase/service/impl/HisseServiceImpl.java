package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.repository.HisseRepository;
import com.stockmarket.studycase.service.HisseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class HisseServiceImpl implements HisseService {
    @Autowired
    private HisseRepository hisseRepository;
    @Override
    public List<HisseSenedi> tumHisseleriGetir() {
        return hisseRepository.findAll();
    }
    @Override
    public Optional<HisseSenedi> hisseBulById(Long id) {
        return hisseRepository.findById(id);
    }
    @Override
    public HisseSenedi hisseKaydet(HisseSenedi hisse) {
        return hisseRepository.save(hisse);
    }
    @Override
    public HisseSenedi hisseGuncelle(HisseSenedi hisse) {
        if (hisseRepository.existsById(hisse.getId())) {
            return hisseRepository.save(hisse); } else { return null;} }
    @Override
    public void hisseSil(Long id) {
        hisseRepository.deleteById(id);
    }
}
