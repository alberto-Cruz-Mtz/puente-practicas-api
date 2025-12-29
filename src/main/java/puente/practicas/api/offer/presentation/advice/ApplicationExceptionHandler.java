package puente.practicas.api.offer.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import puente.practicas.api.common.advice.BaseURI;
import puente.practicas.api.offer.service.exception.ApplicationAlreadyExistsException;
import puente.practicas.api.offer.service.exception.ApplicationNotFoundException;
import puente.practicas.api.offer.service.exception.InvalidApplicationStateException;

import java.net.URI;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleApplicationNotFoundException(ApplicationNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Application Not Found");
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/application-not-found"));

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(InvalidApplicationStateException.class)
    public ResponseEntity<ProblemDetail> handleInvalidApplicationStateException(InvalidApplicationStateException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Invalid Application State");
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/invalid-application-state"));

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(ApplicationAlreadyExistsException.class)
    public ResponseEntity<ProblemDetail> handleApplicationAlreadyExistsException(ApplicationAlreadyExistsException ex) {
        HttpStatus status = HttpStatus.CONFLICT;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        problemDetail.setTitle("Application Already Exists");
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/application-already-exists"));

        return ResponseEntity.status(status).body(problemDetail);
    }
}
