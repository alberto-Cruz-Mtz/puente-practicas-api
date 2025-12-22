package puente.practicas.api.auth.presentation.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}
