package puente.practicas.api.auth.service.exception;

import puente.practicas.api.common.exception.ResourceNotFoundException;

public class RefreshTokenNotFoundException extends ResourceNotFoundException {
    public RefreshTokenNotFoundException(String message) {
        super(message);
    }
}
