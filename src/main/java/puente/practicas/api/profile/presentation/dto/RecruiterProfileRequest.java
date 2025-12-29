package puente.practicas.api.profile.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import puente.practicas.api.common.validation.URL;

import java.util.UUID;

public record RecruiterProfileRequest(
        @NotBlank(message = "username must not be blank")
        @Size(max = 75, message = "username must not exceed {max} characters")
        @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "username can only contain letters, numbers, underscores, and hyphens")
        String username,

        @NotBlank(message = "first name must not be blank")
        @Size(max = 50, message = "first name must not exceed {max} characters")
        String firstName,

        @NotBlank(message = "last name must not be blank")
        @Size(max = 50, message = "last name must not exceed {max} characters")
        String lastName,

        @URL(message = "avatarUrl must be a valid http(s) URL or empty")
        String avatarUrl,

        @NotNull(message = "companyId must not be null")
        UUID companyId
) {
}
