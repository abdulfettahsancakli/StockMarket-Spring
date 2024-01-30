package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.service.SermayeArtisiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sermaye-artisi")
public class SermayeArtisiController {

    @Autowired
    private SermayeArtisiService sermayeArtisiService;

    @GetMapping
    public ResponseEntity<List<SermayeArtisi>> tumSermayeArtislariniGetir() {
        return ResponseEntity.ok(sermayeArtisiService.tumSermayeArtislariniGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SermayeArtisi> sermayeArtisiBulById(@PathVariable Long id) {
        Optional<SermayeArtisi> sermayeArtisi = sermayeArtisiService.sermayeArtisiBulById(id);
        return sermayeArtisi.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<SermayeArtisi> sermayeArtisiKaydet(@RequestBody SermayeArtisi sermayeArtisi) {
        SermayeArtisi savedCapitalIncrease = sermayeArtisiService.sermayeArtisiKaydet(sermayeArtisi);
        return ResponseEntity.ok(savedCapitalIncrease);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SermayeArtisi> sermayeArtisiGuncelle(@PathVariable Long id, @RequestBody SermayeArtisi sermayeArtisi) {
        sermayeArtisi.setId(id);
        SermayeArtisi updatedCapitalIncrease = sermayeArtisiService.sermayeArtisiGuncelle(sermayeArtisi);
        return ResponseEntity.ok(updatedCapitalIncrease);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> sermayeArtisiSil(@PathVariable Long id) {
        sermayeArtisiService.sermayeArtisiSil(id);
        return ResponseEntity.noContent().build();
    }
}
