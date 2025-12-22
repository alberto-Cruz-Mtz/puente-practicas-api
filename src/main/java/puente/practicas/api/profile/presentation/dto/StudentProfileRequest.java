package puente.practicas.api.profile.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record StudentProfileRequest(
        @NotBlank(message = "fullName is required")
        @Size(max = 100, message = "fullName must not exceed 100 characters")
        String fullName,

        @Pattern(regexp = "(^$|^(https?)://.+$)", message = "cvUrl must be a valid URL or empty")
        String cvUrl,

        @Pattern(regexp = "(^$|^(https?)://.+$)", message = "portfolioUrl must be a valid URL or empty")
        String portfolioUrl,

        @Pattern(regexp = "(^$|^(https?)://.+$)", message = "avatarUrl must be a valid URL or empty")
        String avatarUrl) {
}
