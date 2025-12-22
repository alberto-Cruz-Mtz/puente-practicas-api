package puente.practicas.api.auth.configuration.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import puente.practicas.api.auth.service.exception.JwtValidationException;
import puente.practicas.api.auth.util.JwtUtil;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorizationHeader == null || authorizationHeader.isBlank() || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);
        this.authenticateIfNecessary(token);

        filterChain.doFilter(request, response);
    }

    private void authenticateIfNecessary(String token) {
        try {
            DecodedJWT decoded = jwtUtil.validateToken(token);

            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                Authentication authentication = this.buildAuthenticationFromToken(decoded);
                this.addAuthenticationToContext(authentication);
            }
        } catch (JwtValidationException ignored) {
        }
    }

    private Authentication buildAuthenticationFromToken(DecodedJWT decoded) {
        String username = jwtUtil.getUsernameFromToken(decoded);
        String authority = jwtUtil.getAuthorityFromToken(decoded);
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(authority));
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    private void addAuthenticationToContext(Authentication authentication) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

}
