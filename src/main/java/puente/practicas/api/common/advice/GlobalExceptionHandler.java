package puente.practicas.api.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import puente.practicas.api.common.exception.ResourceNotFoundException;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        Map<String, String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (existing, replacement) -> existing));

        String firstErrorMessage = ex.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(FieldError::getDefaultMessage)
                .orElse("validation failed for the request parameters");

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, firstErrorMessage);
        problemDetail.setTitle("Validation Failed");
        problemDetail.setProperty("fieldErrors", fieldErrors);
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/validation-failed"));

        return ResponseEntity.status(status).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGenericException(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500
        String message = "An unexpected error occurred. Please contact support.";
        log.error("Unhandled exception occurred", ex);

        ProblemDetail error = ProblemDetail.forStatusAndDetail(status, message);
        error.setTitle("Internal Server Error");
        error.setType(URI.create(BaseURI.ERROR_URI + "/internal-server-error"));

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        ProblemDetail error = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        error.setTitle("Resource Not Found");
        error.setType(URI.create(BaseURI.ERROR_URI + "/resource-not-found"));

        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        ProblemDetail error = ProblemDetail.forStatusAndDetail(status, ex.getMessage());
        error.setTitle("Bad Request");
        error.setType(URI.create(BaseURI.ERROR_URI + "/bad-request"));

        return ResponseEntity.status(status).body(error);
    }
}
