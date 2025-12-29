package puente.practicas.api.profile.presentation.dto;

public record ProfileSummaryResponse(
        String username,
        String firstName,
        String lastName,
        String avatarUrl
) {
}
