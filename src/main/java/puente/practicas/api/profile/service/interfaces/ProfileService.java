package puente.practicas.api.profile.service.interfaces;

import puente.practicas.api.profile.persistence.entity.ProfileEntity;

import java.util.UUID;

public interface ProfileService {

    ProfileEntity findProfileById(UUID id);

    void ensureProfileNotExists(UUID id);

    void ensureDisplayNameNotInUse(String displayName);
}
