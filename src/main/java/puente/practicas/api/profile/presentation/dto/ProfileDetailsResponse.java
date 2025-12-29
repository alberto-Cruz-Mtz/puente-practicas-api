package puente.practicas.api.profile.presentation.dto;

public record ProfileDetailsResponse(
        String username,
        String firstName,
        String lastName,
        String biography,
        String avatarUrl,
        String portfolioUrl,
        String cvUrl
) {
}
