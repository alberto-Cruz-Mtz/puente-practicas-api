package puente.practicas.api.offer.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.offer.persistence.entity.OfferEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OfferRepository extends JpaRepository<OfferEntity, UUID>, JpaSpecificationExecutor<OfferEntity> {

    Page<OfferEntity> findAllByCompanyOrderByCreatedAt(CompanyEntity company, Pageable pageable);

    Optional<OfferEntity> findByCompany(CompanyEntity company);
}
