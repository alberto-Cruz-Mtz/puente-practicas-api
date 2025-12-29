package puente.practicas.api.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class UrlConstraintsValidator implements ConstraintValidator<URL, String> {

    private Pattern regexp;

    @Override
    public void initialize(URL constraintAnnotation) {
        String pattern = constraintAnnotation.regexp();

        try {
            this.regexp = Pattern.compile(pattern);
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Invalid regex pattern: " + pattern, e);
        }
    }

    @Override
    public boolean isValid(String url, ConstraintValidatorContext context) {
        if (url == null || url.isEmpty()) {
            return true; // Consider null or empty as valid, use @NotNull or @NotBlank for non-empty validation
        }
        return this.regexp.matcher(url).matches();
    }
}
