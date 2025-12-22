package puente.practicas.api.auth.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.persistence.repository.UserRepository;
import puente.practicas.api.auth.service.exception.UserNotFoundException;
import puente.practicas.api.auth.service.interfaces.UserService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public UserEntity findByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public UserEntity findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public boolean isEmailInUse(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        return repository.save(user);
    }
}
