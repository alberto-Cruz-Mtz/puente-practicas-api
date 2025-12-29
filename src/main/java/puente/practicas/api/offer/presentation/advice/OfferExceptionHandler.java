package puente.practicas.api.offer.presentation.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import puente.practicas.api.common.advice.BaseURI;
import puente.practicas.api.offer.service.exception.OfferNotFoundException;

import java.net.URI;

@RestControllerAdvice
public class OfferExceptionHandler {

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleOfferNotFoundException(OfferNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        problemDetail.setTitle("Offer Not Found");
        problemDetail.setType(URI.create(BaseURI.ERROR_URI + "/offer-not-found"));

        return ResponseEntity.status(status).body(problemDetail);
    }
}
