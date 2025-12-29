package puente.practicas.api.offer.presentation.dto;

import puente.practicas.api.offer.persistence.model.ApplicationStatus;

import java.util.UUID;

public record ApplicantsResponse(
        UUID studentId,
        String avatarUrl,
        String studentName,
        String cvUrl,
        ApplicationStatus applicationStatus
) {
}
