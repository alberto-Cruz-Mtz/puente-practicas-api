package puente.practicas.api.offer.presentation.dto;

import java.time.Instant;
import java.util.UUID;

public record OfferSummary(
        UUID id,
        String title,
        String companyName,
        String companyLogoUrl,
        String location,
        String duration,
        String modality,
        Instant postedDate
) {
}
