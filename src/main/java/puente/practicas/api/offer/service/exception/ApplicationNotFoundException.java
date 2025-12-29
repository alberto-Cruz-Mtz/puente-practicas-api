package puente.practicas.api.offer.service.exception;

import puente.practicas.api.common.exception.ResourceNotFoundException;

public class ApplicationNotFoundException extends ResourceNotFoundException {
    public ApplicationNotFoundException(String message) {
        super(message);
    }
}
