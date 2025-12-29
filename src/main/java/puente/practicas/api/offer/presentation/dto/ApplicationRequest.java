package puente.practicas.api.offer.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record ApplicationRequest(
        @NotNull(message = "Offer ID cannot be null")
        UUID offerId
) {
}
