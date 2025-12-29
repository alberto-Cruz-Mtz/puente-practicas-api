package puente.practicas.api.offer.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record OfferDetailsResponse(
        UUID id,
        String title,
        String description,
        String requirements,
        String responsibilities,
        String companyName,
        String companyLogoUrl,
        String location,
        String duration,
        String modality,
        boolean isPaid,
        int vacancies,
        Instant postedDate
) {
}
