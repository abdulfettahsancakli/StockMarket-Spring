package com.stockmarket.studycase.specifications;

import com.stockmarket.studycase.entity.KarPayi;
import com.stockmarket.studycase.entity.KarPayi_;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.entity.Tertip_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class KarPayıSpecification {
    public Specification<KarPayi> searchKarPayiDagitimiByTertip(String tertipRef) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (tertipRef != null) {
                predicates.add(criteriaBuilder.equal(root.join(KarPayi_.tertip).get(Tertip_.TERTIP_ID), UUID.fromString(tertipRef)));
            }
            query.orderBy(criteriaBuilder.desc(root.get(KarPayi_.id)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<KarPayi> searchKarPayiDagitimiByTertipandYil(Tertip tertip, Integer yil){
        return(root, query, criteriaBuilder) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.get(KarPayi_.TERTIP),tertip));
            predicates.add(criteriaBuilder.equal(root.get(KarPayi_.DAGITIM_YILI),yil));
            query.orderBy(criteriaBuilder.desc(root.get(KarPayi_.id)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<KarPayi> searchKarPayiDagitimiByTertipAndYilAndYilİciSeri(String tertipRef,Integer yil,Integer yilIciSeri){
        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.equal(root.join(KarPayi_.TERTIP).get(Tertip_.TERTIP_ID), UUID.fromString(tertipRef)));
            predicates.add(criteriaBuilder.equal(root.get(KarPayi_.DAGITIM_YILI), yil));
            predicates.add(criteriaBuilder.equal(root.get(KarPayi_.YIL_ICINDE_SERI), yilIciSeri));
            query.orderBy(criteriaBuilder.desc(root.get(KarPayi_.id)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
