package puente.practicas.api.company.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.company.persistence.repository.CompanyRepository;
import puente.practicas.api.company.presentation.dto.CompanyDetailsResponse;
import puente.practicas.api.company.presentation.dto.CompanyRequest;
import puente.practicas.api.company.presentation.dto.CompanySummaryResponse;
import puente.practicas.api.company.service.exception.AttributeDuplicateException;
import puente.practicas.api.company.service.exception.CompanyNotFoundException;
import puente.practicas.api.company.service.interfaces.CompanyService;
import puente.practicas.api.company.util.CompanyMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper mapper;

    @Override
    @Transactional
    public CompanySummaryResponse createCompany(CompanyRequest request) {
        this.validateUniqueFields(request);
        CompanyEntity company = mapper.toEntity(request);

        var savedCompany = companyRepository.save(company);
        return mapper.toCompanyResponse(savedCompany);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyEntity getCompanyById(UUID id) {
        return this.findCompanyById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CompanyDetailsResponse getCompanyDetailsById(UUID id) {
        CompanyEntity company = this.findCompanyById(id);
        return mapper.toCompanyDetailsResponse(company);
    }

    private CompanyEntity findCompanyById(UUID id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException("Company not found register in the system"));
    }

    private void validateUniqueFields(CompanyRequest request) {
        if (companyRepository.existsByRfc(request.rfc())) {
            throw new AttributeDuplicateException("A company with the same RFC already exists.", request.rfc());
        }

        if (companyRepository.existsByEmail(request.email())) {
            throw new AttributeDuplicateException("A company with the same email already exists.", request.email());
        }

        if (companyRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new AttributeDuplicateException("A company with the same phone number already exists.", request.phoneNumber());
        }
    }
}