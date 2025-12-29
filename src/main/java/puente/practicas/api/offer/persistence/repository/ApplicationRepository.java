package puente.practicas.api.offer.persistence.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import puente.practicas.api.offer.persistence.entity.ApplicationEntity;
import puente.practicas.api.offer.persistence.entity.ApplicationId;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;

import java.util.UUID;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, ApplicationId> {

    boolean existsById(@NonNull ApplicationId id);

    Slice<ApplicationEntity> findAllByOffer(OfferEntity offer, Pageable pageable);

    Slice<ApplicationEntity> findAllByStudentProfile(StudentProfileEntity studentProfile, Pageable pageable);
}
