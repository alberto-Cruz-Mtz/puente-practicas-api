package puente.practicas.api.company.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import puente.practicas.api.common.advice.BaseURI;
import puente.practicas.api.company.service.exception.AttributeDuplicateException;
import puente.practicas.api.company.service.exception.CompanyNotFoundException;

import java.net.URI;

@RestControllerAdvice
public class CompanyExceptionHandler {

    @ExceptionHandler(AttributeDuplicateException.class)
    public ResponseEntity<ProblemDetail> handleDuplicate(AttributeDuplicateException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setType(URI.create(BaseURI.ERROR_URI + "/duplicate-attribute"));
        problem.setTitle("Duplicate Attribute");
        problem.setProperty("duplicatedField", ex.getAttributeName());
        return ResponseEntity.status(409).body(problem);
    }

    @ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFoundException(CompanyNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setTitle("Company Not Found");
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/company-not-found"));

        return ResponseEntity.status(status).body(problemDetail);
    }
}
