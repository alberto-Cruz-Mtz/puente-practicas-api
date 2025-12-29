package puente.practicas.api.offer.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ApplyRequest(
        @NotNull(message = "Offer ID cannot be null")
        UUID offerId,
        @NotNull(message = "Student ID cannot be null")
        UUID studentId) {
}
