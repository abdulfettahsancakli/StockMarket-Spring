package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.service.HisseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hisse")
public class HisseController {

    @Autowired
    private HisseService hisseService;

    @GetMapping
    public ResponseEntity<List<HisseSenedi>> tumHisseleriGetir() {
        return ResponseEntity.ok(hisseService.tumHisseleriGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HisseSenedi> hisseBulById(@PathVariable Long id) {
        Optional<HisseSenedi> hisse = hisseService.hisseBulById(id);
        return hisse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HisseSenedi> hisseKaydet(@RequestBody HisseSenedi hisse) {
        HisseSenedi savedShare = hisseService.hisseKaydet(hisse);
        return ResponseEntity.ok(savedShare);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HisseSenedi> hisseGuncelle(@PathVariable Long id, @RequestBody HisseSenedi hisse) {
        hisse.setId(id);
        HisseSenedi updatedShare = hisseService.hisseGuncelle(hisse);
        return ResponseEntity.ok(updatedShare);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> hisseSil(@PathVariable Long id) {
        hisseService.hisseSil(id);
        return ResponseEntity.noContent().build();
    }
}
