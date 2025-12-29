package puente.practicas.api.company.util;

import org.springframework.stereotype.Component;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.company.presentation.dto.CompanyDetailsResponse;
import puente.practicas.api.company.presentation.dto.CompanyRequest;
import puente.practicas.api.company.presentation.dto.CompanySummaryResponse;

@Component
public class CompanyMapper {

    public CompanySummaryResponse toCompanyResponse(CompanyEntity company) {
        return new CompanySummaryResponse(
                company.getId(),
                company.getLegalName(),
                company.getAddress(),
                company.getPhoneNumber(),
                company.getEmail(),
                company.getLogoUrl()
        );
    }

    public CompanyEntity toEntity(CompanyRequest request) {
        return CompanyEntity.builder()
                .legalName(request.legalName())
                .tradeName(request.tradeName())
                .rfc(request.rfc())
                .description(request.description())
                .address(request.address())
                .phoneNumber(request.phoneNumber())
                .email(request.email())
                .websiteUrl(request.websiteUrl())
                .logoUrl(request.logoUrl())
                .build();
    }

    public CompanyDetailsResponse toCompanyDetailsResponse(CompanyEntity company) {
        return new CompanyDetailsResponse(
                company.getTradeName(),
                company.getDescription(),
                company.getAddress(),
                company.getPhoneNumber(),
                company.getEmail(),
                company.getWebsiteUrl(),
                company.getLogoUrl(),
                company.isVerified()
        );
    }
}
