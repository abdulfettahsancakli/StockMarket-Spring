package com.stockmarket.studycase.controller;

import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.models.HissedarAddModel;
import com.stockmarket.studycase.models.HissedarSearchModel;
import com.stockmarket.studycase.service.HissedarlarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hissedarlar")
@Tag(name = "HissedarlarController", description = "To perform operations on hissedarlar")
public class HissedarlarController {


    private HissedarlarService hissedarlarService;

    @Autowired
    public HissedarlarController(HissedarlarService hissedarlarService) {
        this.hissedarlarService = hissedarlarService;
    }


    @Operation(
            summary = "GET operations on hissedarlar by using hissedar id",
            description = "It is used to retrive hissedar object in database using hissedar id"
    )
    @GetMapping("/getir/{id}")
    public ResponseEntity<Hissedarlar> getirHissedar(@PathVariable Long id) {
        Optional<Hissedarlar> hissedar = hissedarlarService.HissedarBul_Id(id);
        return hissedar.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/sil/{id}")
    public ResponseEntity<String> silHissedar(@PathVariable Long id) {
        hissedarlarService.hissedarSil(id);
        return ResponseEntity.ok("Hissedar başarıyla silindi. Hissedar ID: " + id);
    }

    @Operation(
            summary = "GET operations on hissedarlar ",
            description = "It is used to retrive hissedar object in database"
    )
    @GetMapping("/listele")
    public ResponseEntity<List<Hissedarlar>> listHissedarlar() {
        List<Hissedarlar> hissedarlar = hissedarlarService.listHissedarlar();
        return new ResponseEntity<>(hissedarlar, HttpStatus.OK);
    }

    @Operation(
            summary = "Post operations on hissedarlar",
            description = "It is used to save hissedar object in database"
    )
    @PostMapping("/ekle")
    public ResponseEntity<Hissedarlar> hissedarEkle(@RequestBody HissedarAddModel hissedarAddModel) {
        Hissedarlar hissedar = hissedarlarService.hissedarEkle(hissedarAddModel);
        return ResponseEntity.ok(hissedar);
    }

    // @GetMapping("/search")
    // public List<Hissedarlar> searchHissedarlarByCriteria(@RequestBody HissedarSearchModel hissedarSearchModel) {
    //    return hissedarlarService.listHissedarlarBySearch(hissedarSearchModel); }

    @GetMapping("/search")
    public List<Hissedarlar> searchHissedarlarByCriteria(
            @RequestParam(required = false) String unvan,
            @RequestParam(required = false) String yatirimciTipi,
            @RequestParam(required = false) String telefon,
            @RequestParam(required = false) String hissedarSicilNumarasi) {

        HissedarSearchModel searchModel = new HissedarSearchModel();
        searchModel.setUnvan(unvan);
        searchModel.setYatirimciTipi(YatirimciTipi.valueOf(yatirimciTipi));
        searchModel.setTelefon(telefon);
        searchModel.setHissedarSicilNumarasi(hissedarSicilNumarasi);

        System.out.println(searchModel);

        return hissedarlarService.listHissedarlarBySearch(searchModel);
    }
}
