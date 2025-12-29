package puente.practicas.api.auth.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import puente.practicas.api.auth.persistence.model.AccountType;

public record RegisterRequest(
        @Email(message = "Email should be valid format address")
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 20, message = "Password must be between {min} and {max} characters")
        String password,

        @Pattern(regexp = "STUDENT|RECRUITER", message = "Account type must be one of the predefined values")
        @NotBlank(message = "Account type is required")
        AccountType accountType
) {
}
