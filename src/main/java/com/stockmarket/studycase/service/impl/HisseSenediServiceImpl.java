package com.stockmarket.studycase.service.impl;

import com.stockmarket.studycase.entity.*;
import com.stockmarket.studycase.enums.IslemTipi;
import com.stockmarket.studycase.exception.*;
import com.stockmarket.studycase.models.HisseSenediOlusturModel;
import com.stockmarket.studycase.repository.*;
import com.stockmarket.studycase.service.HisseSenediService;
import com.stockmarket.studycase.service.KuponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HisseSenediServiceImpl implements HisseSenediService {
    @Autowired
    private HisseSenediRepository hisseSenediRepository;

    @Autowired
    private HissedarRepository hissedarRepository;

    @Autowired
    private TertipRepository tertipRepository;

    @Autowired
    private KuponService kuponService;

    @Autowired
    private KuponRepository kuponRepository;

    @Autowired
    private IslemlerRepository islemlerRepository;

    @Override
    public void hisseSenediVer(Long hisseSenediId, Long hissedarId) {

        // Girişlerin null olup olmadığını kontrol et
        if (hisseSenediId == null || hissedarId == null) {
            throw new IllegalArgumentException("Hisse senedi ID veya hissedar ID null olamaz");
        }

        // Hisse senedi ve hissedarı bul
        HisseSenedi hisseSenedi = hisseSenediRepository.findById(hisseSenediId)
                .orElseThrow(() -> new HisseSenediNotFoundException("Hisse senedi bulunamadı"));

        Hissedarlar hissedar = hissedarRepository.findById(hissedarId)
                .orElseThrow(() -> new HissedarNotFoundException("Hissedar bulunamadı"));

        // Hisse senedinin daha önce bir hissedara verilip verilmediğini kontrol et
        if (hisseSenedi.getHissedar() != null) {
            throw new HisseSenediZatenVerilmisException("Hisse senedi zaten bir hissedara verilmiş");
        }

        List<Kupon> kuponlar = kuponRepository.findByHisseSenediId(hisseSenediId);

        // Null küpür numarasına sahip kuponları filtrele
        List<Kupon> payAlmaKuponlar = kuponlar.stream()
                .filter(kupon -> kupon.getKupur_no() != null)
                .collect(Collectors.toList());

        // Küpür numarasına göre sırala
        payAlmaKuponlar.sort(Comparator.comparing(Kupon::getKupur_no));

        // En küçük küpür numaralı kuponu seç
        Kupon enKucukKupon = payAlmaKuponlar.get(0);

        // Küpür numarasını kullanıldı olarak işaretle
        enKucukKupon.setUsed(true);

        // Hisse senedine hissedarı set et
        hisseSenedi.setHissedar(hissedar);
        hisseSenedi.setHisseVerildiMi(Boolean.TRUE);

        // Hisse senedi ve kuponları veritabanına kaydet (transaction management)
        try {
            hisseSenediRepository.save(hisseSenedi);
            kuponRepository.save(enKucukKupon);

            // İşlem kaydını oluştur ve kaydet
            Islemler islem = new Islemler();
            islem.setIslemTipi(IslemTipi.HISSE_SENEDI);
            islem.setKupon(enKucukKupon); // Hangi kuponla yapıldığı bilgisini kupon olarak ayarla
            islem.setIslem_zamani(LocalDateTime.now());
            islem.setKarpayiDonemi(null);// Hisse senedi verme işlemi olduğu için kar payı dönemi null olacak
            islem.setKarpayi_tutari(0); // Hisse senedi verme işleminde kar payı tutarı 0 olacak

            islemlerRepository.save(islem);
        } catch (HisseSenediKuponNotSavedException e) {
            throw new HisseSenediKuponNotSavedException("Hisse senedi veya kupon kaydedilirken bir hata oluştu", e);
        }
    }

    @Override
    public List<Kupon> HisseSenediKuponlariGetir(Long hisseSenediId) {
        return kuponService.getKuponlarByHisseSenediId(hisseSenediId);
    }

    @Override
    public HisseSenedi HisseSenediOlustur(List<HisseSenediOlusturModel> senetBilgileri, Long tertipId) {
        Tertip tertip = tertipRepository.findById(tertipId)
                .orElseThrow(() -> new TertipNotFoundException("Tertip bulunamadı"));

        Integer enYuksekSeriNumarasi = hisseSenediRepository.findMaxSeriNumarasiByTertipId(tertipId);
        if (enYuksekSeriNumarasi == null) {
            enYuksekSeriNumarasi = 0;
        }

        // Adet sayısına göre senet bilgilerini küçükten büyüğe sırala
        Collections.sort(senetBilgileri, Comparator.comparingDouble(HisseSenediOlusturModel::getNominal));

        for (HisseSenediOlusturModel senetBilgi : senetBilgileri) {
            for (int i = 0; i < senetBilgi.getAdet(); i++) {
                HisseSenedi hisseSenedi = new HisseSenedi();
                hisseSenedi.setSeriNumarasi(++enYuksekSeriNumarasi);
                hisseSenedi.setTertip(tertip);
                hisseSenedi.setNominalDeger(senetBilgi.getNominal());

                hisseSenedi = hisseSenediRepository.save(hisseSenedi);

                // Hisse senedine bağlı kuponları oluştur
                List<Kupon> kuponlar = kuponService.kuponOlustur(hisseSenedi);
                hisseSenedi.setKuponList(kuponlar);

                hisseSenediRepository.save(hisseSenedi);
            }
        }

        // En son oluşturulan hisse senedini döndür
        return hisseSenediRepository.findById(Long.valueOf(enYuksekSeriNumarasi)).orElseThrow(() -> new RuntimeException("Hisse senedi oluşturulamadı"));
    }




}
