package puente.practicas.api.profile.presentation.controller;

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
import puente.practicas.api.auth.util.SecurityUtils;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;
import puente.practicas.api.profile.service.interfaces.StudentProfileService;

import java.net.URI;

@RestController
@RequestMapping("/student-profiles")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;
    private final SecurityUtils securityUtils;

    @PostMapping
    public ResponseEntity<ProfileSummaryResponse> create(@RequestBody @Valid StudentProfileRequest request) {
        String userEmail = this.securityUtils.getAuthenticatedUserEmail();
        ProfileSummaryResponse response = studentProfileService.createStudentProfile(request, userEmail);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/me")
                .build()
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileDetailsResponse> get() {
        String userEmail = this.securityUtils.getAuthenticatedUserEmail();
        ProfileDetailsResponse response = studentProfileService.getStudentProfileByEmail(userEmail);
        return ResponseEntity.ok(response);
    }

    // Get profile by username for public access
    @GetMapping("/{username}")
    public ResponseEntity<ProfileDetailsResponse> getByUsername(@PathVariable String username) {
        ProfileDetailsResponse response = studentProfileService.getStudentProfileByUsername(username);
        return ResponseEntity.ok(response);
    }
}