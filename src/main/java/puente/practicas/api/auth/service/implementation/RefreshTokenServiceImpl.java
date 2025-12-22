package puente.practicas.api.auth.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.auth.persistence.entity.RefreshTokenEntity;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.persistence.repository.RefreshTokenRepository;
import puente.practicas.api.auth.persistence.repository.UserRepository;
import puente.practicas.api.auth.service.exception.RefreshTokenExpiredException;
import puente.practicas.api.auth.service.exception.RefreshTokenNotFoundException;
import puente.practicas.api.auth.service.exception.UserNotFoundException;
import puente.practicas.api.auth.service.interfaces.RefreshTokenService;
import puente.practicas.api.auth.service.interfaces.UserService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private Long expirationTime;

    private final RefreshTokenRepository repository;
    private final UserService userService;

    @Override
    public RefreshTokenEntity findByToken(String refreshToken) {
        return repository.findByToken(refreshToken).orElseThrow(() -> new RefreshTokenNotFoundException("refresh refreshToken not found"));
    }

    @Override
    public RefreshTokenEntity createRefreshToken(UUID userId) {
        UserEntity user = userService.findById(userId);
        RefreshTokenEntity refreshToken = RefreshTokenEntity.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(expirationTime))
                .build();
        return repository.save(refreshToken);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(RefreshTokenEntity refreshToken) {
        boolean isExpired = refreshToken.getExpiryDate().isBefore(Instant.now());

        if (isExpired) {
            repository.delete(refreshToken);
            throw new RefreshTokenExpiredException("refresh refreshToken expired please login again");
        }

        return refreshToken;
    }

    @Override
    @Transactional
    public int deleteByUserId(UUID userId) {
        UserEntity user = userService.findById(userId);
        return repository.deleteByUser(user);
    }
}
