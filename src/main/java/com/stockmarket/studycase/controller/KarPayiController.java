package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.KarPayi;
import com.stockmarket.studycase.service.KarPayiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/kar-payi")
public class KarPayiController {

    @Autowired
    private KarPayiService karPayiService;

    @PostMapping("/dagit")
    public ResponseEntity<String> karPayiDagit(@RequestParam Long tertipId, @RequestParam Double dagitimOrani,
                                               @RequestParam Integer dagitimYili) {
        karPayiService.karPayiDagit(tertipId, dagitimOrani, dagitimYili);
        return new ResponseEntity<>("Kar payı dağıtımı başarıyla gerçekleştirildi.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<KarPayi>> tumKarPaylariGetir() {
        return ResponseEntity.ok(karPayiService.tumKarPaylariGetir());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KarPayi> karPayiBulById(@PathVariable Long id) {
        Optional<KarPayi> karPayi = karPayiService.karPayiBulById(id);
        return karPayi.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<KarPayi> karPayiKaydet(@RequestBody KarPayi karPayi) {
        KarPayi savedDividend = karPayiService.karPayiKaydet(karPayi);
        return ResponseEntity.ok(savedDividend);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KarPayi> karPayiGuncelle(@PathVariable Long id, @RequestBody KarPayi karPayi) {
        karPayi.setId(id);
        KarPayi updatedDividend = karPayiService.karPayiGuncelle(karPayi);
        return ResponseEntity.ok(updatedDividend);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> karPayiSil(@PathVariable Long id) {
        karPayiService.karPayiSil(id);
        return ResponseEntity.noContent().build();
    }
}
