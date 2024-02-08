package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kupon")
public class KuponController {

    @Autowired
    private KuponService kuponService;

    @PostMapping("/olustur")
    public ResponseEntity<List<Kupon>> kuponOlustur(@RequestBody HisseSenedi hisseSenedi) {
        List<Kupon> kuponlar = kuponService.kuponOlustur(hisseSenedi);
        return new ResponseEntity<>(kuponlar, HttpStatus.CREATED);
    }

    @GetMapping("/{hisseSenediId}")
    public ResponseEntity<List<Kupon>> getKuponlarByHisseSenediId(@PathVariable Long hisseSenediId) {
        List<Kupon> kuponlar = kuponService.getKuponlarByHisseSenediId(hisseSenediId);
        return new ResponseEntity<>(kuponlar, HttpStatus.OK);
    }


}
