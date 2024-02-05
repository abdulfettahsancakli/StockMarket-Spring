package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.service.SermayeArtisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sermaye-artisi")
public class SermayeArtisiController {

    @Autowired
    private SermayeArtisiService sermayeArtisiService;

    @GetMapping("/tum-sermaye-artislari")
    public ResponseEntity<List<SermayeArtisi>> getTumSermayeArtislari() {
        List<SermayeArtisi> sermayeArtislari = sermayeArtisiService.TumSermayeArtislari();
        return new ResponseEntity<>(sermayeArtislari, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SermayeArtisi> getSermayeArtisiById(@PathVariable Long id) {
        SermayeArtisi sermayeArtisi = sermayeArtisiService.IdyeGoreSermayeArtisi(id);
        if (sermayeArtisi != null) {
            return new ResponseEntity<>(sermayeArtisi, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/search-by-tertip/{tertipNo}")
    public List<SermayeArtisi> searchSermayeArtisiByTertip(@PathVariable String tertipNo) {
        return sermayeArtisiService.searchSermayeArtisiByTertip(tertipNo);
    }

    @PostMapping("/olustur")
    public ResponseEntity<SermayeArtisi> createSermayeArtisi(
            @RequestParam Double bedelliArtis,
            @RequestParam Double bedelsizArtis,
            @RequestParam Double artisOrani,
            @RequestParam Double eskiSermaye
            )
    {
        SermayeArtisi SermayeArtisiOlustur = sermayeArtisiService.SermayeArtisiOlustur(bedelliArtis, bedelsizArtis, artisOrani, eskiSermaye);
        return new ResponseEntity<>(SermayeArtisiOlustur, HttpStatus.CREATED);
    }
}
