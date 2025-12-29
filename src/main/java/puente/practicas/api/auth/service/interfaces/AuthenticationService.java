package puente.practicas.api.auth.service.interfaces;

import puente.practicas.api.auth.presentation.dto.AuthenticationRequest;
import puente.practicas.api.auth.presentation.dto.AuthenticationResponse;
import puente.practicas.api.auth.presentation.dto.RefreshTokenRequest;
import puente.practicas.api.auth.presentation.dto.RefreshTokenResponse;
import puente.practicas.api.auth.presentation.dto.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse login(AuthenticationRequest request);

    AuthenticationResponse signup(RegisterRequest request);

    RefreshTokenResponse refreshToken(RefreshTokenRequest request);

    void logout(String email);
}
