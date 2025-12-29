package puente.practicas.api.offer.presentation.dto;

import java.util.List;

public record ApplicationListResponse(
        List<ApplicationResponse> data,
        Pagination pagination
) {
}
