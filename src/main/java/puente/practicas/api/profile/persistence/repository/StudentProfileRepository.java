package puente.practicas.api.profile.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;

import java.util.Optional;
import java.util.UUID;

public interface StudentProfileRepository extends JpaRepository<StudentProfileEntity, UUID> {

    Optional<StudentProfileEntity> findByDisplayName(String displayName);
}
