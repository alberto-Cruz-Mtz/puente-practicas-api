package puente.practicas.api.offer.presentation.dto;

import puente.practicas.api.offer.persistence.model.ApplicationStatus;

import java.time.Instant;
import java.util.UUID;

public record ApplicationResponse(
        UUID offerId,
        String offerTitle,
        ApplicationStatus status,
        Instant applicationDate
) {
}
