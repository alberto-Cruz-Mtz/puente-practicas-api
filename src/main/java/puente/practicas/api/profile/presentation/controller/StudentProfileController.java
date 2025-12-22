package puente.practicas.api.profile.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;
import puente.practicas.api.profile.service.interfaces.SetUpProfileService;

import java.net.URI;

@RestController
@RequestMapping("/student-profile")
@RequiredArgsConstructor
public class StudentProfileController {

    private final SetUpProfileService setUpProfileService;

    @PostMapping
    public ResponseEntity<ProfileResponse> createStudentProfile(@RequestBody @Valid StudentProfileRequest request) {
        String userEmail = this.getAuthenticatedUserEmail();
        ProfileResponse response = setUpProfileService.createStudentProfile(request, userEmail);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .build()
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<ProfileDetailsResponse> getStudentProfile() {
        String userEmail = this.getAuthenticatedUserEmail();
        ProfileDetailsResponse response = setUpProfileService.getStudentProfile(userEmail);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{userEmail}")
    public ResponseEntity<ProfileDetailsResponse> getStudentProfile(@PathVariable String userEmail) {
        ProfileDetailsResponse response = setUpProfileService.getStudentProfile(userEmail);
        return ResponseEntity.ok(response);
    }

    private String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }
}
