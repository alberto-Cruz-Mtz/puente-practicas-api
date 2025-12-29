package puente.practicas.api.company.service.interfaces;

import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.company.presentation.dto.CompanyDetailsResponse;
import puente.practicas.api.company.presentation.dto.CompanyRequest;
import puente.practicas.api.company.presentation.dto.CompanySummaryResponse;

import java.util.UUID;

public interface CompanyService {

    CompanySummaryResponse createCompany(CompanyRequest request);

    CompanyEntity getCompanyById(UUID id);

    CompanyDetailsResponse getCompanyDetailsById(UUID id);
}
