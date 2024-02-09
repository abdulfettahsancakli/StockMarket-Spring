package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Islemler;
import com.stockmarket.studycase.models.HisseSenediAddModel;
import com.stockmarket.studycase.models.KarPayiDağitModel;
import com.stockmarket.studycase.models.KarpayiIcinSenetAramaModel;
import com.stockmarket.studycase.service.IslemlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/islemler")
public class IslemlerController {

    @Autowired
    private final IslemlerService islemlerService;

    @Autowired
    public IslemlerController(IslemlerService islemlerService) {
        this.islemlerService = islemlerService;
    }

    @GetMapping("/hisse-senedi-ara")
    public ResponseEntity<List<HisseSenedi>> hisseSenediAra(@RequestBody HisseSenediAddModel hisseSenediAddModel) {
        List<HisseSenedi> hisseSenediList = islemlerService.hisseSenediAra(hisseSenediAddModel);
        return ResponseEntity.ok(hisseSenediList);
    }

    @PostMapping("/hisse-senedi-ver")
    public ResponseEntity<Void> hisseSenediVer(@RequestBody List<HisseSenediAddModel> hisseSenediAddModelList) {
        islemlerService.hisseSenediVer(hisseSenediAddModelList);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/kar-payi-icin-uygun-hisse-senedi-listele")
    public ResponseEntity<List<HisseSenedi>> karPayıIcinUygunHisseSenediListele(@RequestBody KarpayiIcinSenetAramaModel karpayiIcinSenetAramaModel) {
        List<HisseSenedi> hisseSenediList = islemlerService.karPayıIcinUygunHisseSenediListele(karpayiIcinSenetAramaModel);
        return ResponseEntity.ok(hisseSenediList);
    }

    @PostMapping("/kar-payi-ver")
    public ResponseEntity<Void> karPayiVer(@RequestBody List<KarPayiDağitModel> listKarPayiDagitModel) {
        islemlerService.karPayiVer(listKarPayiDagitModel);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Islemler> findIslemById(@PathVariable String id) {
        Islemler islem = islemlerService.findIslemById(id);
        if (islem != null) {
            return ResponseEntity.ok(islem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
