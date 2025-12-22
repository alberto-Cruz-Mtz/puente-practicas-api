package puente.practicas.api.auth.service.exception;

import com.auth0.jwt.exceptions.JWTVerificationException;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String invalidJwtToken, JWTVerificationException verificationException) {
        super(invalidJwtToken, verificationException);
    }
}
