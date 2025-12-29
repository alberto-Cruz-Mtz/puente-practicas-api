package puente.practicas.api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UrlConstraintsValidator.class})
public @interface URL {

    String regexp() default "^(https?)://.+$";

    String message() default "must be a valid URL starting with http or https";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
