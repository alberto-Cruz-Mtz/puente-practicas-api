package puente.practicas.api.offer.presentation.dto;

import puente.practicas.api.offer.persistence.model.WorkModality;

import java.time.Instant;

public record OfferFilter(
        WorkModality modality,
        Instant publishedAfter,
        boolean hasFinancialSupport
) {
}