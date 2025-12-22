package puente.practicas.api.auth.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import puente.practicas.api.auth.persistence.entity.RefreshTokenEntity;
import puente.practicas.api.auth.persistence.entity.UserEntity;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {

    Optional<RefreshTokenEntity> findByToken(String token);

    @Modifying
    int deleteByUser(UserEntity user);
}
