package com.stockmarket.studycase.specifications;
/*
import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.entity.Hissedarlar_;
import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.models.OrtakSearchModel;
import com.stockmarket.studycase.util.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import javax.persistence.criteria.Predicate;


@Component
public class HissedarlarSpecification {
    public Specification<Hissedarlar> searchHissedarlarBySearchModel(OrtakSearchModel ortakSearchModel) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (ortakSearchModel.getUnvan() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("unvan")), "%" + StringUtils.toUpperCaseTurkish(ortakSearchModel.getUnvan()).trim() + "%"));
            }


         query.orderBy(criteriaBuilder.asc(root.get(Hissedarlar_UNVAN)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

*/
