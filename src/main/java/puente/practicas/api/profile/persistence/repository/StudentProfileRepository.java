package puente.practicas.api.profile.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;

import java.util.Optional;
import java.util.UUID;

public interface StudentProfileRepository extends JpaRepository<StudentProfileEntity, UUID> {
}
