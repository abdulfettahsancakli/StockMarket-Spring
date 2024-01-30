package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.service.HissedarlarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hissedarlar")
public class HissedarlarController {

    @Autowired
    private HissedarlarService hissedarlarService;

    @PostMapping("/ekle")
    public ResponseEntity<Hissedarlar> ekleHissedar(@RequestBody Hissedarlar hissedar) {
        Hissedarlar savedHissedar = hissedarlarService.saveHissedar(hissedar);
        return new ResponseEntity<>(savedHissedar, HttpStatus.CREATED);
    }

    @PutMapping("/guncelle")
    public ResponseEntity<Hissedarlar> guncelleHissedar(@RequestBody Hissedarlar hissedar) {
        Hissedarlar updatedHissedar = hissedarlarService.updateHissedar(hissedar);
        return new ResponseEntity<>(updatedHissedar, HttpStatus.OK);
    }

    @GetMapping("/getir/{id}")
    public ResponseEntity<Hissedarlar> getirHissedar(@PathVariable Long id) {
        Optional<Hissedarlar> hissedar = hissedarlarService.HissedarBul_Id(id);
        return hissedar.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<Void> silHissedar(@PathVariable Long id) {
        hissedarlarService.hissedarSil(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/listele")
    public ResponseEntity<List<Hissedarlar>> listeleHissedarlar() {
        List<Hissedarlar> hissedarlar = hissedarlarService.listHissedarlar();
        return new ResponseEntity<>(hissedarlar, HttpStatus.OK);
    }
}
