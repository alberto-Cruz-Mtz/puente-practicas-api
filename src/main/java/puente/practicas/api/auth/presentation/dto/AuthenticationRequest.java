package puente.practicas.api.auth.presentation.dto;

import puente.practicas.api.auth.persistence.model.AccountType;

public record AuthenticationRequest(
        String email,
        String password,
        AccountType accountType
) {
}
