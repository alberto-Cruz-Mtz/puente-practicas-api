package puente.practicas.api.offer.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import puente.practicas.api.auth.util.SecurityUtils;
import puente.practicas.api.offer.presentation.dto.ApplicantListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationRequest;
import puente.practicas.api.offer.presentation.dto.StatusChangeRequest;
import puente.practicas.api.offer.service.interfaces.ApplicationService;

import java.util.UUID;

@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;
    private final SecurityUtils securityUtils;

    @PostMapping // apply to offer by student
    public ResponseEntity<Void> apply(@RequestBody @Valid ApplicationRequest request) {
        UUID studentId = securityUtils.getAuthenticatedUserId();
        applicationService.applyToOffer(request.offerId(), studentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping// withdraw application by student
    public ResponseEntity<Void> withdrawApplication(@RequestBody @Valid ApplicationRequest request) {
        UUID studentId = securityUtils.getAuthenticatedUserId();
        applicationService.withdrawApplication(request.offerId(), studentId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/status") // only for the recruiter to change application status
    public ResponseEntity<Void> updateApplicationStatus(@RequestBody @Valid StatusChangeRequest request) {
        applicationService.updateApplicationStatus(request);
        return ResponseEntity.noContent().build();
    }

    @GetMapping // only for the recruiter to see applicants for their offer
    public ResponseEntity<ApplicantListResponse> getApplicantsByOfferId(
            @RequestParam UUID offerId,
            @PageableDefault(size = 10, sort = "appliedAt", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        var response = applicationService.getApplicantsByOfferId(offerId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/me") // only for the student to see their applications
    public ResponseEntity<ApplicationListResponse> getApplicationsByStudentId(
            @PageableDefault(size = 10, sort = "appliedAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        UUID studentId = securityUtils.getAuthenticatedUserId();
        var response = applicationService.getApplicationsByStudentId(studentId, pageable);
        return ResponseEntity.ok(response);
    }
}