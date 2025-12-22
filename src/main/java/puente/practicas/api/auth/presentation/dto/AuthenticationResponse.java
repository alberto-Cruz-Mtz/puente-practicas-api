package puente.practicas.api.auth.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record AuthenticationResponse(
        UUID id,
        String accessToken,
        String refreshToken,
        Instant timestamp
) {
}
