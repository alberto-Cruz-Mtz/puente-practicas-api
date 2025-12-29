package puente.practicas.api.company.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import puente.practicas.api.common.validation.URL;

public record CompanyRequest(
        @NotBlank(message = "legalName must not be blank")
        String legalName,

        @NotBlank(message = "tradeName must not be blank")
        String tradeName,

        @NotBlank(message = "rfc must not be blank")
        @Pattern(regexp = "^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$", message = "invalid RFC format")
        String rfc, // Mexican RFC format

        @Size(max = 500, message = "description must be at most 500 characters")
        String description,

        @Size(max = 255, message = "address must be at most 255 characters")
        @NotBlank(message = "address must not be blank")
        String address,

        @Pattern(regexp = "^(\\+\\d{1,3}[- ]?)?\\d{7,15}$", message = "invalid phone number format")
        @NotBlank(message = "phoneNumber must not be blank")
        String phoneNumber,

        @Email(message = "invalid email format")
        @NotBlank(message = "email must not be blank")
        String email,

        @URL(message = "websiteUrl must be a valid URL")
        String websiteUrl,

        @URL(message = "logoUrl must be a valid URL")
        String logoUrl) {
}
