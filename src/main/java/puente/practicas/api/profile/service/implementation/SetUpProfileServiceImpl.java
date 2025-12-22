package puente.practicas.api.profile.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.service.interfaces.UserService;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.persistence.repository.StudentProfileRepository;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;
import puente.practicas.api.profile.service.exception.StudentProfileNotFoundException;
import puente.practicas.api.profile.service.interfaces.SetUpProfileService;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SetUpProfileServiceImpl implements SetUpProfileService {

    private final StudentProfileRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public ProfileResponse createStudentProfile(StudentProfileRequest request, String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);
        StudentProfileEntity entity = StudentProfileEntity.builder()
                .user(user)
                .fullName(request.fullName())
                .avatarUrl(request.avatarUrl())
                .portfolioUrl(request.portfolioUrl())
                .cvUrl(request.cvUrl())
                .build();

        StudentProfileEntity savedStudentProfile = repository.save(entity);

        return new ProfileResponse(savedStudentProfile.getFullName(), savedStudentProfile.getAvatarUrl());
    }

    @Override
    public ProfileDetailsResponse getStudentProfile(String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);
        StudentProfileEntity studentProfile = this.findById(user.getUid());

        return new ProfileDetailsResponse(studentProfile.getFullName(), studentProfile.getAvatarUrl(),
                studentProfile.getPortfolioUrl(), studentProfile.getCvUrl());
    }

    private StudentProfileEntity findById(UUID uid) {
        return repository.findById(uid)
                .orElseThrow(() -> new StudentProfileNotFoundException("Student profile with id " + uid + " not found"));
    }
}
