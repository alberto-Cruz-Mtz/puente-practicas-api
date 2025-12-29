package puente.practicas.api.company.service.exception;

import lombok.Getter;

public class AttributeDuplicateException extends RuntimeException {
    @Getter
    private String attributeName;

    public AttributeDuplicateException(String message) {
        super(message);
    }

    public AttributeDuplicateException(String message, String attributeName) {
        super(message);
        this.attributeName = attributeName;
    }
}
