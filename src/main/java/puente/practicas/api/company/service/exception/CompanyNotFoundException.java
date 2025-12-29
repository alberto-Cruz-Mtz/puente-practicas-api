package puente.practicas.api.company.service.exception;

import puente.practicas.api.common.exception.ResourceNotFoundException;

public class CompanyNotFoundException extends ResourceNotFoundException {
    public CompanyNotFoundException(String message) {
        super(message);
    }
}
