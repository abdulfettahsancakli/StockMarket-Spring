package com.stockmarket.studycase.specifications;

import com.stockmarket.studycase.entity.Hissedarlar;
import com.stockmarket.studycase.entity.Hissedarlar_;
import com.stockmarket.studycase.enums.YatirimciTipi;
import com.stockmarket.studycase.models.HissedarSearchModel;
import com.stockmarket.studycase.util.StringUtils;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class HissedarlarSpecification {
    public Specification<Hissedarlar> searchHissedarlarBySearchModel(HissedarSearchModel hissedarSearchModel) {
        return (root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (hissedarSearchModel.getUnvan() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("unvan")),
                        "%" + StringUtils.toUpperCaseTurkish(hissedarSearchModel.getUnvan().trim()) + "%"));
            }

            if (hissedarSearchModel.getYatirimciTipi() != null) {
                predicates.add(criteriaBuilder.equal(root.get("yatirimciTipi"), hissedarSearchModel.getYatirimciTipi()));
            }

            if (hissedarSearchModel.getTelefon() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("telefon"), hissedarSearchModel.getTelefon().trim()));
            }

            if (hissedarSearchModel.getHissedarSicilNumarasi() != null ) {
                predicates.add(criteriaBuilder.equal(root.get("sicilNumarasi"), hissedarSearchModel.getHissedarSicilNumarasi().trim()));
            }


         query.orderBy(criteriaBuilder.asc(root.get(Hissedarlar_.UNVAN)));
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}


