package puente.practicas.api.company.presentation.dto;

import puente.practicas.api.company.persistence.model.VerificationStatus;

public record CompanyDetailsResponse(
        String tradeName,
        String description,
        String address,
        String phoneNumber,
        String email,
        String websiteUrl,
        String logoUrl,
        boolean isVerified
) {
}
