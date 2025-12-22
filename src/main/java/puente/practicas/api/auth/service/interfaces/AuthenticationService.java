package puente.practicas.api.auth.service.interfaces;

import puente.practicas.api.auth.presentation.dto.AuthenticationRequest;
import puente.practicas.api.auth.presentation.dto.AuthenticationResponse;
import puente.practicas.api.auth.presentation.dto.RefreshToken;
import puente.practicas.api.auth.presentation.dto.RefreshTokenResponse;

import java.util.UUID;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse signup(AuthenticationRequest request);

    RefreshTokenResponse refreshToken(RefreshToken request);

    void logout(String email);
}
