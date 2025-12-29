package puente.practicas.api.offer.service.exception;

import puente.practicas.api.common.exception.ResourceNotFoundException;

public class OfferNotFoundException extends ResourceNotFoundException {
    public OfferNotFoundException(String message) {
        super(message);
    }
}
