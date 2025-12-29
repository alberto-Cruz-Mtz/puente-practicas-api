package puente.practicas.api.offer.persistence.specification;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.offer.presentation.dto.OfferFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OfferSpecification {

    public static Specification<OfferEntity> withFilter(OfferFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("isActive")));

            if (filter.hasFinancialSupport()) {
                predicates.add(criteriaBuilder.isTrue(root.get("isPaid")));
            }

            if (filter.modality() != null) {
                predicates.add(criteriaBuilder.equal(root.get("modality"), filter.modality()));
            }

            if (filter.publishedAfter() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), filter.publishedAfter()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
