package puente.practicas.api.profile.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import puente.practicas.api.common.advice.BaseURI;
import puente.practicas.api.common.exception.ResourceNotFoundException;
import puente.practicas.api.profile.service.exception.ProfileAlreadyExistsException;
import puente.practicas.api.profile.service.exception.ProfileNotFoundException;
import puente.practicas.api.profile.service.exception.RecruiterProfileNotFoundException;
import puente.practicas.api.profile.service.exception.StudentProfileNotFoundException;
import puente.practicas.api.profile.service.exception.UsernameAlreadyInUseException;

import java.net.URI;

@RestControllerAdvice
public class ProfileExceptionHandler {

    @ExceptionHandler({StudentProfileNotFoundException.class, RecruiterProfileNotFoundException.class, ProfileNotFoundException.class})
    public ResponseEntity<ProblemDetail> handleProfileNotFoundException(ProfileNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Profile Not Found");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/errors/profile-not-found"));

        return ResponseEntity.status(404).body(problemDetail);
    }

    @ExceptionHandler(ProfileAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleProfileAlreadyExistsException(ProfileAlreadyExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Profile Already Configured");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/errors/profile-already-configured"));

        return ResponseEntity.status(409).body(problemDetail);
    }

    @ExceptionHandler(UsernameAlreadyInUseException.class)
    public ResponseEntity<ProblemDetail> handleUsernameAlreadyInUseException(UsernameAlreadyInUseException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Username Already In Use");
        problemDetail.setType(URI.create(BaseURI.BASE_URI + "/errors/username-already-in-use"));

        return ResponseEntity.status(409).body(problemDetail);
    }
}
