package com.stockmarket.studycase.specifications;

import com.stockmarket.studycase.entity.*;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class KuponSpecification {

    public Specification<Kupon> searchPayAlmaKuponuByTertipNo(Integer tertipNo){
        return(root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();
            if(tertipNo!=null){
                predicates.add(criteriaBuilder.equal(root.join(Kupon_.HISSE_SENEDI).join(HisseSenedi_.TERTIP).get(Tertip_.TERTIP_NO),tertipNo));
            }
            predicates.add(criteriaBuilder.isNull(root.get(Kupon_.KUPUR_NO)));

            query.orderBy(criteriaBuilder.asc(root.get(Kupon_.KUPUR_NO)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Kupon> searchPayAlmaKuponuBySenet(HisseSenedi hisseSenedi){
        return(root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();
          if(hisseSenedi!=null){
              predicates.add(criteriaBuilder.equal(root.get(Kupon_.HISSE_SENEDI),hisseSenedi));
          }

          predicates.add(criteriaBuilder.isNotNull(root.get(Kupon_.KUPUR_NO)));
          query.orderBy(criteriaBuilder.asc(root.get(Kupon_.KUPUR_NO)));
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Kupon> searchKarPayiKuponuByHisseSenedi(HisseSenedi hisseSenedi){
        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
           if(hisseSenedi!=null){
               predicates.add(criteriaBuilder.equal(root.get(Kupon_.HISSE_SENEDI),hisseSenedi));
           }
           predicates.add(criteriaBuilder.isNull(root.get(Kupon_.KUPUR_NO)));

           query.orderBy(criteriaBuilder.asc(root.get(Kupon_.YIL)));

           return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Kupon> searchKuponByTertipAndOrtak(Tertip tertip,Hissedarlar hissedarlar){
        return(root, query, criteriaBuilder) -> {
          List<Predicate> predicates = new ArrayList<>();
          predicates.add(criteriaBuilder.equal(root.join(Kupon_.HISSE_SENEDI).get(HisseSenedi_.TERTIP),tertip));
          predicates.add(criteriaBuilder.equal(root.join(Kupon_.HISSE_SENEDI).get(HisseSenedi_.HISSEDAR),hissedarlar));
          predicates.add(criteriaBuilder.equal(root.get(Kupon_.IS_USED),Boolean.FALSE));
          predicates.add(criteriaBuilder.isNotNull(root.get(Kupon_.YIL)));
          query.orderBy(criteriaBuilder.asc(root.get(Kupon_.YIL)));
          return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

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
