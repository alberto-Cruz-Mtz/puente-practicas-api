package puente.practicas.api.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import puente.practicas.api.auth.service.interfaces.UserService;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SecurityUtils {

    private final UserService userService;

    public String getAuthenticatedUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) throw new IllegalStateException("No authentication found in security context");

        return authentication.getName();
    }

    public UUID getAuthenticatedUserId() {
        String email = getAuthenticatedUserEmail();
        return userService.findIdByEmail(email);
    }
}
