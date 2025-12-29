package puente.practicas.api.auth.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import puente.practicas.api.auth.service.exception.EmailAlreadyInUseException;
import puente.practicas.api.auth.service.exception.JwtValidationException;
import puente.practicas.api.auth.service.exception.RefreshTokenNotFoundException;
import puente.practicas.api.auth.service.exception.UserNotFoundException;
import puente.practicas.api.common.advice.BaseURI;
import puente.practicas.api.common.exception.ResourceNotFoundException;

import java.net.URI;

@RestControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleAuthenticationException(AuthenticationException ex) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String detailMessage = "the provided username or password is incorrect.";

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, detailMessage);
        problemDetail.setTitle("Authentication Failed");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/authentication/bad-credentials"));
        return ResponseEntity.status(401).body(problemDetail);
    }

    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ProblemDetail> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Email Already In Use");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/authentication/email-already-in-use"));
        return ResponseEntity.status(409).body(problemDetail);
    }

    @ExceptionHandler({UserNotFoundException.class, RefreshTokenNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleNotFoundException(ResourceNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setTitle("Resource Not Found");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/authentication/resource-not-found"));
        return ResponseEntity.status(404).body(problemDetail);
    }
}
