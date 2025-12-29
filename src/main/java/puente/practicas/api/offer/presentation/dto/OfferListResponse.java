package puente.practicas.api.offer.presentation.dto;

import java.util.List;

public record OfferListResponse(
        List<OfferSummary> data,
        Pagination pagination
) {
}
