package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.repository.HisseSenediRepository;
import com.stockmarket.studycase.repository.HissedarRepository;
import com.stockmarket.studycase.repository.KuponRepository;
import com.stockmarket.studycase.service.HisseSenediService;
import com.stockmarket.studycase.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HisseSenediServiceImpl implements HisseSenediService {
    @Autowired
    private HisseSenediRepository hisseSenediRepository;

    @Autowired
    private HissedarRepository hissedarRepository;

    @Autowired
    private KuponRepository kuponRepository;

    @Autowired
    private KuponService kuponService;

    @Override
    public HisseSenedi HisseSenediOlustur(Long hissedarId, Long tertipId, Double nominalDeger) {

        HisseSenedi hisseSenedi = new HisseSenedi();
        hisseSenedi.setSeriNumarasi(1);
        hisseSenedi.setTertipId(tertipId);
        hisseSenedi.setNominalDeger(nominalDeger);
        if (hissedarId != null) {
            hisseSenedi.setHissedarId(hissedarId);
            hisseSenedi.setHisseVerildiMi(true);
        } else {
            hisseSenedi.setHisseVerildiMi(false);
        }
        hisseSenedi = hisseSenediRepository.save(hisseSenedi);

        // Hisse senedine bağlı kuponları oluştur
        List<Kupon> kuponlar = kuponService.kuponOlustur(hisseSenedi);
        hisseSenedi.setKuponList(kuponlar);

        return hisseSenediRepository.save(hisseSenedi);
    }
    @Override
    public void hisseSenediVer(Long hisseSenediId, Long hissedarId) {

        Optional<HisseSenedi> optionalHisseSenedi = hisseSenediRepository.findById(hisseSenediId);
        Optional<Hissedarlar> optionalHissedar = hissedarRepository.findById(hissedarId);

        if (optionalHisseSenedi.isPresent() && optionalHissedar.isPresent()) {
            HisseSenedi hisseSenedi = optionalHisseSenedi.get();
            Hissedarlar hissedar = optionalHissedar.get();

            if (hisseSenedi.getHissedar() != null) {
                throw new RuntimeException("Hisse senedi zaten bir hissedara verilmiş");
            }

            Tertip tertip = hisseSenedi.getTertip();

            // Hisse senedine ait pay alma kuponlarını getir (1'den 16'ya kadar olanlar) ?
            // payAlmaKuponlarına erişmem lazım
            List<Kupon> payAlmaKuponlar = kuponService.getKuponlarByHisseSenediId(hisseSenediId);

            // Küpür numarası en küçük olan pay alma kuponunu bul
            Kupon enKucukKupon = payAlmaKuponlar.stream()
                    .min(Comparator.comparing(Kupon::getKupur_no))
                    .orElseThrow(() -> new RuntimeException("Pay alma kuponu bulunamadı"));

            // Küpür numarasını kullanıldı olarak işaretle
            enKucukKupon.setUsed(true);
            kuponRepository.save(enKucukKupon);

            // Hisse senedine hissedarı set et
            hisseSenedi.setHissedar(hissedar);

            // Kupon kullanıldı olarak işaretlencekk

            hisseSenediRepository.save(hisseSenedi);
        } else {
            throw new RuntimeException("Hisse senedi veya hissedar bulunamadı");
        }
    }

    @Override
    public List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId) {
        return kuponService.getKuponlarByHisseSenediId(hisseSenediId);
    }
}
