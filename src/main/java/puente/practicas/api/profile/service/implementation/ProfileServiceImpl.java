package puente.practicas.api.profile.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import puente.practicas.api.profile.persistence.entity.ProfileEntity;
import puente.practicas.api.profile.persistence.repository.ProfileRepository;
import puente.practicas.api.profile.service.exception.ProfileAlreadyExistsException;
import puente.practicas.api.profile.service.exception.ProfileNotFoundException;
import puente.practicas.api.profile.service.exception.UsernameAlreadyInUseException;
import puente.practicas.api.profile.service.interfaces.ProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository repository;

    @Override
    public ProfileEntity findProfileById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ProfileNotFoundException("profile not found, set up your profile first"));
    }

    @Override
    public void ensureProfileNotExists(UUID id) {
        if (repository.existsById(id)) {
            throw new ProfileAlreadyExistsException("Profile already exists, cannot create a new one for the same user");
        }
    }

    @Override
    public void ensureDisplayNameNotInUse(String displayName) {
        if (repository.existsByDisplayName(displayName)) {
            throw new UsernameAlreadyInUseException("Username already in use by another user registered in the system");
        }
    }
}