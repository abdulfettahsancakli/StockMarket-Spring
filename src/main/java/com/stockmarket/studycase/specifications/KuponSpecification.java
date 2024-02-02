package com.stockmarket.studycase.specifications;

import com.stockmarket.studycase.entity.HisseSenedi;
import com.stockmarket.studycase.entity.Kupon;
import com.stockmarket.studycase.entity.Kupon_;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class KuponSpecification {

    public Specification<Kupon> searchEnKucukKupurNoluPayAlmaKuponuListBySenetList(List<HisseSenedi> HisseSenedi , Long kupurNo){
        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates  = new ArrayList<>();
            if(HisseSenedi!=null){
                predicates.add(criteriaBuilder.in(root.get(Kupon_.HISSE_SENEDI)).value(HisseSenedi));
            }
            predicates.add(criteriaBuilder.equal(root.get(Kupon_.KUPUR_NO),kupurNo));

            query.orderBy(criteriaBuilder.asc(root.get(Kupon_.KUPON_NO)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
