package puente.practicas.api.profile.service.exception;

import puente.practicas.api.common.exception.ResourceNotFoundException;

public class StudentProfileNotFoundException extends ResourceNotFoundException {
    public StudentProfileNotFoundException(String message) {
        super(message);
    }
}
