package puente.practicas.api.profile.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.profile.persistence.entity.RecruiterProfileEntity;

import java.util.UUID;

public interface RecruiterProfileRepository extends JpaRepository<RecruiterProfileEntity, UUID> {
}
