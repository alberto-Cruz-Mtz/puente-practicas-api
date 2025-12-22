package puente.practicas.api.auth.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import puente.practicas.api.auth.service.exception.JwtValidationException;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiration}")
    private Long expirationTime;

    public String generateToken(Authentication authentication) {
        Algorithm algorithm = Algorithm.HMAC512(secret);
        String username = authentication.getName();
        String authorities = this.getAuthorities(authentication);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(username)
                .withClaim("authority", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expirationTime))
                .withNotBefore(new Date(System.currentTimeMillis()))
                .withKeyId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public DecodedJWT validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(this.issuer)
                    .build();
            return verifier.verify(token);
        } catch (JWTVerificationException verificationException) {
            throw new JwtValidationException("JWT validation failed: the token is invalid or expired", verificationException);
        }
    }

    public String getUsernameFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }

    public String getAuthorityFromToken(DecodedJWT decodedJWT) {
        return decodedJWT.getClaim("authority").asString();
    }

    private String getAuthorities(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
    }
}