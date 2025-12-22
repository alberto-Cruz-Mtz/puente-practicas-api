package puente.practicas.api.auth.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.auth.persistence.entity.RefreshTokenEntity;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.persistence.model.AccountType;
import puente.practicas.api.auth.persistence.model.UserRole;
import puente.practicas.api.auth.persistence.repository.RefreshTokenRepository;
import puente.practicas.api.auth.persistence.repository.UserRepository;
import puente.practicas.api.auth.presentation.dto.AuthenticationRequest;
import puente.practicas.api.auth.presentation.dto.AuthenticationResponse;
import puente.practicas.api.auth.presentation.dto.RefreshToken;
import puente.practicas.api.auth.presentation.dto.RefreshTokenResponse;
import puente.practicas.api.auth.service.exception.EmailAlreadyInUseException;
import puente.practicas.api.auth.service.interfaces.AuthenticationService;
import puente.practicas.api.auth.service.interfaces.RefreshTokenService;
import puente.practicas.api.auth.service.interfaces.UserService;
import puente.practicas.api.auth.util.JwtUtil;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication credentials = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(credentials);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity user = userService.findByEmail(userDetails.getUsername());

        String token = jwtUtil.generateToken(authentication);
        RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getUid());

        return new AuthenticationResponse(user.getUid(), token, refreshToken.getToken(), Instant.now());
    }

    @Override
    @Transactional
    public AuthenticationResponse signup(AuthenticationRequest request) {
        boolean isEmailUsed = userService.isEmailInUse(request.email());
        if (isEmailUsed) throw new EmailAlreadyInUseException("This email is already in use by another account.");
        UserRole role = mapAccountTypeToUserRole(request.accountType());
        String passwordEncoded = passwordEncoder.encode(request.password());

        UserEntity user = UserEntity.builder()
                .email(request.email())
                .password(passwordEncoded)
                .role(role)
                .build();

        UserEntity userSaved = userService.createUser(user);

        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved.getEmail(), null, authorities);

        String accessToken = jwtUtil.generateToken(authentication);
        var refreshToken = refreshTokenService.createRefreshToken(userSaved.getUid());

        return new AuthenticationResponse(userSaved.getUid(), accessToken, refreshToken.getToken(), Instant.now());
    }

    @Override
    public void logout(String email) {
        UserEntity user = userService.findByEmail(email);
        refreshTokenService.deleteByUserId(user.getUid());
    }

    @Override
    @Transactional
    public RefreshTokenResponse refreshToken(RefreshToken request) {
        RefreshTokenEntity usedToken = refreshTokenService.findByToken(request.refreshToken());
        refreshTokenService.verifyExpiration(usedToken);

        UserEntity user = usedToken.getUser();
        refreshTokenRepository.delete(usedToken);

        var authorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, authorities);

        String newAccessToken = jwtUtil.generateToken(authentication);
        RefreshTokenEntity newRefreshToken = refreshTokenService.createRefreshToken(user.getUid());

        return new RefreshTokenResponse(newAccessToken, newRefreshToken.getToken());
    }

    private UserRole mapAccountTypeToUserRole(AccountType accountType) {
        return switch (accountType) {
            case RECRUITER_ACCOUNT -> UserRole.ROLE_RECRUITER;
            case STUDENT_ACCOUNT -> UserRole.ROLE_STUDENT;
            default -> throw new IllegalArgumentException("Invalid account type");
        };
    }
}