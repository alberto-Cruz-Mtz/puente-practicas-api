package puente.practicas.api.company.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puente.practicas.api.company.presentation.dto.CompanyDetailsResponse;
import puente.practicas.api.company.presentation.dto.CompanyRequest;
import puente.practicas.api.company.presentation.dto.CompanySummaryResponse;
import puente.practicas.api.company.service.interfaces.CompanyService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanySummaryResponse> create(@RequestBody @Valid CompanyRequest request) {
        var response = companyService.createCompany(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{companyId}")
                .buildAndExpand(response.id())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/{companyId}")
    public ResponseEntity<CompanyDetailsResponse> get(@PathVariable UUID companyId) {
        var response = companyService.getCompanyDetailsById(companyId);
        return ResponseEntity.ok(response);
    }
}
