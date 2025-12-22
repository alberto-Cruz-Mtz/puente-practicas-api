package puente.practicas.api.profile.presentation.dto;

public record ProfileDetailsResponse(
        String fullName,
        String avatarUrl,
        String portfolioUrl,
        String cvUrl
) {
}
