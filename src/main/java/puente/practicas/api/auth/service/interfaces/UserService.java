package puente.practicas.api.auth.service.interfaces;

import puente.practicas.api.auth.persistence.entity.UserEntity;

import java.util.UUID;

public interface UserService {

    UserEntity findByEmail(String email);

    UserEntity findById(UUID id);

    boolean isEmailInUse(String email);

    UserEntity createUser(UserEntity user);
}
