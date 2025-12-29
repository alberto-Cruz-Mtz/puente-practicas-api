package puente.practicas.api.company.presentation.dto;

import java.util.UUID;

public record CompanySummaryResponse(
        UUID id,
        String name,
        String address,
        String phoneNumber,
        String email,
        String logoUrl
) {
}
