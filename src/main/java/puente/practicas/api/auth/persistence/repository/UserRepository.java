package puente.practicas.api.auth.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import puente.practicas.api.auth.persistence.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmail(String email);

    @Query("SELECT u.uid FROM UserEntity u WHERE u.email = :email")
    Optional<UUID> findIdByEmail(String email);

    boolean existsByEmail(String email);
}
