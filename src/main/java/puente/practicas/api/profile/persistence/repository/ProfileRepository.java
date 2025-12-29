package puente.practicas.api.profile.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puente.practicas.api.profile.persistence.entity.ProfileEntity;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {

    boolean existsByDisplayName(String displayName);
}
