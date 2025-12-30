package puente.practicas.api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = EnumConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumValidation {

    Class<? extends Enum<?>> enumClass();

    String message() default "Value is not valid enum type";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
