package puente.practicas.api.auth.service.interfaces;

import puente.practicas.api.auth.persistence.entity.RefreshTokenEntity;

import java.util.UUID;

public interface RefreshTokenService {

    RefreshTokenEntity findByToken(String refreshToken);

    RefreshTokenEntity createRefreshToken(UUID userId);

    RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken);

    int deleteByUserId(UUID userId);
}

