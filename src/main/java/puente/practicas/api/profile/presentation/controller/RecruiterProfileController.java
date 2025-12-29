package puente.practicas.api.profile.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puente.practicas.api.auth.util.SecurityUtils;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.RecruiterProfileRequest;
import puente.practicas.api.profile.service.interfaces.RecruiterProfileService;

import java.net.URI;

@RestController
@RequestMapping("/recruiter-profiles")
@RequiredArgsConstructor
public class RecruiterProfileController {

    private final RecruiterProfileService recruiterProfileService;
    private final SecurityUtils securityUtils;

    @PostMapping
    public ResponseEntity<ProfileSummaryResponse> create(@RequestBody @Valid RecruiterProfileRequest request) {
        String userEmail = securityUtils.getAuthenticatedUserEmail();
        ProfileSummaryResponse response = recruiterProfileService.createRecruiterProfile(request, userEmail);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/me").build().toUri();
        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileSummaryResponse> get() {
        String userEmail = securityUtils.getAuthenticatedUserEmail();
        ProfileSummaryResponse response = recruiterProfileService.getRecruiterProfile(userEmail);
        return ResponseEntity.ok(response);
    }
}
