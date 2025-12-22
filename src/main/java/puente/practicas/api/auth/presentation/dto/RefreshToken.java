package puente.practicas.api.auth.presentation.dto;

import jakarta.validation.constraints.NotEmpty;

public record RefreshToken(
        @NotEmpty(message = "Refresh refreshToken must not be empty") String refreshToken
) {
}
