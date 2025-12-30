package puente.practicas.api.offer.presentation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import puente.practicas.api.common.validation.EnumValidation;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;

import java.util.UUID;

public record StatusChangeRequest(
        @NotNull(message = "Offer ID cannot be null")
        UUID offerId,

        @NotNull(message = "Student ID cannot be null")
        UUID studentId,

        @NotEmpty(message = "New status cannot be empty")
        @EnumValidation(enumClass = ApplicationStatus.class, message = "Status must be one of: APPLIED, IN_REVIEW, REJECTED, ACCEPTED")
        String newStatus
) {
}
