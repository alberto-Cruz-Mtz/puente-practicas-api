package puente.practicas.api.offer.presentation.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;

import java.util.UUID;

public record StatusChangeRequest(
        @NotNull(message = "Offer ID cannot be null")
        UUID offerId,
        @NotNull(message = "Student ID cannot be null")
        UUID studentId,
        @NotNull(message = "New status cannot be null")
        @Pattern(regexp = "PENDING|IN_REVIEW|ACCEPTED|REJECTED", message = "Invalid status value")
        ApplicationStatus newStatus
) {
}
