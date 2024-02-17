package com.stockmarket.studycase.service.impl;


import com.stockmarket.studycase.entity.*;
import com.stockmarket.studycase.enums.IslemTipi;
import com.stockmarket.studycase.enums.KuponTuru;
import com.stockmarket.studycase.enums.converters.IslemTipiConverter;
import com.stockmarket.studycase.exception.TertipNotFoundException;
import com.stockmarket.studycase.models.KarPayiRequest;
import com.stockmarket.studycase.repository.*;
import com.stockmarket.studycase.service.KarPayiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KarPayiServiceImpl implements KarPayiService {

    @Autowired
    private KarPayiRepository karPayiRepository;

    @Autowired
    private HisseSenediRepository hisseSenediRepository;

    @Autowired
    private TertipRepository tertipRepository;

    @Autowired
    private KuponRepository kuponRepository;

    @Autowired
    private IslemlerRepository islemlerRepository;

    @Override
    public void karPayiDagit(Long tertipId, Double dagitimOrani, Integer dagitimYili) {

        // Verilen tertip ID'sine sahip tertip var mı kontrol et
        Tertip tertip = tertipRepository.findById(tertipId)
                .orElseThrow(() -> new TertipNotFoundException("Tertip bulunamadı: " + tertipId));

        // Yıl içindeki seri numarasını belirle
        int yilIcindeSeri = karPayiRepository.findMaxSeriNumarasiByDagitimYili(dagitimYili) + 1;

        List<HisseSenedi> hisseSenediList = tertip.getHisseSenediList();

        // Kar payı dağıtımını gerçekleştir
        for (HisseSenedi hisseSenedi : hisseSenediList) {
            // Hisse senedinin hissedarını al
            Hissedarlar hissedar = hisseSenedi.getHissedar();

            // Eğer hissedar bilgisi yoksa veya hisse senedi verilmediyse bu hisse senedi üzerinden kar payı dağıtma işlemine devam etme
            if (hissedar == null || !hisseSenedi.getHisseVerildiMi()) {
                continue;
            }

            // Hisse senedinin kar payı kuponlarını al
            List<Kupon> kuponList = hisseSenedi.getKuponList();

            // Kar payı kuponlarını işle
            for (Kupon kupon : kuponList) {
                if (kupon.getKuponTuru().equals(KuponTuru.KAR_PAYI) && kupon.getYil() == dagitimYili ) {
                    // Kupon boşsa kullan
                    kupon.setUsed(Boolean.TRUE);
                    kuponRepository.save(kupon);

                    // Kar payı kaydı oluştur ve kaydet
                    KarPayi karPayi = new KarPayi();
                    karPayi.setDagitimOrani(dagitimOrani);
                    karPayi.setDagitimYili(dagitimYili);
                    karPayi.setYilIcindeSeri(yilIcindeSeri);
                    karPayi.setTertip(tertip);
                    karPayiRepository.save(karPayi);

                    // Kar payı dağıtımı yapıldıktan sonra işlemi sonlandır

                    // İşlem kaydını oluştur
                    Islemler islem = new Islemler();
                    islem.setIslemTipi(IslemTipi.KAR_PAYI);
                    islem.setKupon(kupon);
                    islem.setIslem_zamani(LocalDateTime.now());
                    islem.setKarpayiDonemi(null);
                    islem.setKarpayi_tutari(karPayi.getDagitimOrani() * hisseSenedi.getNominalDeger());

                    islemlerRepository.save(islem);

                    break;
                }
            }
        }
    }

    @Override
    public List<KarPayi> tumKarPaylariGetir() {
        return karPayiRepository.findAll();
    }

    @Override
    public Optional<KarPayi> karPayiBulById(Long id) {
        return karPayiRepository.findById(id);
    }

    @Override
    public KarPayi karPayiKaydet(KarPayi karPayi) {
        return karPayiRepository.save(karPayi);
    }

    @Override
    public KarPayi karPayiGuncelle(KarPayi karPayi) {
        if (karPayiRepository.existsById(karPayi.getId())) {
            return karPayiRepository.save(karPayi);
        } else {
            return null;
        }
    }

    @Override
    public void karPayiSil(Long id) {
        karPayiRepository.deleteById(id);
    }
}
