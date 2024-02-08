package com.stockmarket.studycase.specifications;
import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.models.HisseSenediOlusturModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import jakarta.persistence.criteria.Predicate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
public class HisseSenediSpecification {

    public Specification<HisseSenedi> NominallereGoreKucuktenBuyuge(List<HisseSenediOlusturModel> senetBilgileri) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Senet bilgilerini küçükten büyüğe sırala
            senetBilgileri.sort(Comparator.comparingDouble(HisseSenediOlusturModel::getNominal));

            int seriNumarasi = 1; // İlk seri numarası

            for (HisseSenediOlusturModel senetBilgi : senetBilgileri) {
                // Nominale göre seri numarası atama kuralı: Artarak seri numarası atama
                predicates.add(criteriaBuilder.equal(root.get("nominalDeger"), senetBilgi.getNominal()));
                predicates.add(criteriaBuilder.equal(root.get("seriNumarasi"), seriNumarasi));

                // Seri numarasını bir sonraki senet için artır
                seriNumarasi++;

                // Başka kriterler de eklenebilir
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
