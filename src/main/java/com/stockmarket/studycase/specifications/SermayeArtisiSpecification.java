package com.stockmarket.studycase.specifications;

import com.stockmarket.studycase.entity.SermayeArtisi;
import com.stockmarket.studycase.entity.SermayeArtisi_;
import com.stockmarket.studycase.entity.Tertip;
import com.stockmarket.studycase.entity.Tertip_;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SermayeArtisiSpecification {

    public Specification<SermayeArtisi> searchSermayeArtisiByTertip(String tertipNo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (tertipNo != null) {
                Join<SermayeArtisi, Tertip> tertipJoin = root.join(SermayeArtisi_.tertip);
                predicates.add(criteriaBuilder.equal(tertipJoin.get(Tertip_.TERTIP_NO), tertipNo));
            }

            query.orderBy(criteriaBuilder.desc(root.get(SermayeArtisi_.id)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
