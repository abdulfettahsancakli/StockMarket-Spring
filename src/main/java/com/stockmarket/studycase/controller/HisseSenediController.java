package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.models.HisseSenediOlusturModel;
import com.stockmarket.studycase.models.HisseSenediRequest;
import com.stockmarket.studycase.models.HisseSenediVerRequest;
import com.stockmarket.studycase.service.HisseSenediService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/hisseSenedi")
public class HisseSenediController {

    private static final Logger logger = LoggerFactory.getLogger(HisseSenediController.class);

    @Autowired
    private HisseSenediService hisseSenediService;

    @PostMapping("/olustur")
    public ResponseEntity<HisseSenedi> HisseSenediOluştur
            (@RequestParam Long tertipId, @RequestParam Double nominalDeger)
    {
        // RequestParam ile gelen değerlerim
        logger.info("tertipId: {}, nominalDeger: {}", tertipId, nominalDeger);
        HisseSenedi HisseSenediOlustur = hisseSenediService.HisseSenediOlustur(tertipId, nominalDeger);
        return ResponseEntity.ok(HisseSenediOlustur);
    }

  /*  @PostMapping("/createHisseSenedi")
    public ResponseEntity<HisseSenedi> createHisseSenedi(@RequestBody List<HisseSenediOlusturModel> senetBilgileri, @RequestParam Long tertipId) {
        HisseSenedi hisseSenedi = hisseSenediService.SenetOlustur(senetBilgileri, tertipId);
        return new ResponseEntity<>(hisseSenedi, HttpStatus.CREATED);
    }
   */

    @PostMapping("/createHisseSenedi")
    public ResponseEntity<HisseSenedi> createHisseSenedi(@RequestBody HisseSenediRequest request) {
        HisseSenedi hisseSenedi = hisseSenediService.SenetOlustur(request.getSenetler(), request.getTertipId());
        return new ResponseEntity<>(hisseSenedi, HttpStatus.CREATED);
    }

    @PostMapping("/ver")
    public ResponseEntity<Void> hisseSenediVer(
            @RequestParam Long hisseSenediId,
            @RequestParam Long hissedarId)
    {
        hisseSenediService.hisseSenediVer(hisseSenediId, hissedarId);
        return ResponseEntity.noContent().build();
    }



    @GetMapping("/{id}/kuponlar")
    public ResponseEntity<List<Kupon>> hisseSenediKuponlariGetir(@PathVariable Long id)
    {
        List<Kupon> kuponlar = hisseSenediService.HisseSenediKuponlariGetir(id);
        return ResponseEntity.ok(kuponlar);
    }


}
