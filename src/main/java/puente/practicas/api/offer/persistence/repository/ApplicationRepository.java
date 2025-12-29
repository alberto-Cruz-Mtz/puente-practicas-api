package puente.practicas.api.offer.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puente.practicas.api.offer.persistence.entity.ApplicationEntity;
import puente.practicas.api.offer.persistence.entity.ApplicationId;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, ApplicationId> {

    boolean existsById(ApplicationId id);
}
