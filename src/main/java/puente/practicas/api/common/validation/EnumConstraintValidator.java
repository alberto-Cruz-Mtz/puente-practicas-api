package puente.practicas.api.common.validation;

import jakarta.validation.ConstraintValidator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumConstraintValidator implements ConstraintValidator<EnumValidation, String> {

    private Set<String> acceptedValues;

    @Override
    public void initialize(EnumValidation annotation) {
        this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null) return true;
        return acceptedValues.contains(value.toUpperCase());
    }
}
