package puente.practicas.api.auth.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import puente.practicas.api.auth.presentation.dto.AuthenticationRequest;
import puente.practicas.api.auth.presentation.dto.AuthenticationResponse;
import puente.practicas.api.auth.presentation.dto.RefreshTokenRequest;
import puente.practicas.api.auth.presentation.dto.RefreshTokenResponse;
import puente.practicas.api.auth.presentation.dto.RegisterRequest;
import puente.practicas.api.auth.service.interfaces.AuthenticationService;
import puente.practicas.api.auth.util.SecurityUtils;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final SecurityUtils securityUtils;

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> signup(@RequestBody @Valid RegisterRequest request) {
        var response = authenticationService.signup(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest request) {
        var response = authenticationService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        String currentPrincipalName = securityUtils.getAuthenticatedUserEmail();

        authenticationService.logout(currentPrincipalName);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request) {
        var response = authenticationService.refreshToken(request);
        return ResponseEntity.ok(response);
    }
}
