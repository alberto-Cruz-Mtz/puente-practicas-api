package puente.practicas.api.profile.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.service.interfaces.UserService;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.persistence.repository.StudentProfileRepository;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;
import puente.practicas.api.profile.service.exception.StudentProfileNotFoundException;
import puente.practicas.api.profile.service.interfaces.ProfileService;
import puente.practicas.api.profile.service.interfaces.StudentProfileService;
import puente.practicas.api.profile.util.ProfileMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    @Override
    @Transactional
    public ProfileSummaryResponse createStudentProfile(StudentProfileRequest request, String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);

        profileService.ensureProfileNotExists(user.getUid());
        profileService.ensureDisplayNameNotInUse(request.username());

        StudentProfileEntity entity = profileMapper.toStudentProfileEntity(request, user);
        StudentProfileEntity savedStudentProfile = studentProfileRepository.save(entity);
        return profileMapper.toProfileSummaryResponse(savedStudentProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileDetailsResponse getStudentProfileByEmail(String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);
        StudentProfileEntity studentProfile = this.findStudentById(user.getUid());
        return profileMapper.toProfileDetailsResponse(studentProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileDetailsResponse getStudentProfileByUsername(String username) {
        StudentProfileEntity studentProfile = this.findProfileByUsername(username);
        return profileMapper.toProfileDetailsResponse(studentProfile);
    }

    @Override
    public StudentProfileEntity findStudentById(UUID id) {
        return studentProfileRepository.findById(id)
                .orElseThrow(() -> new StudentProfileNotFoundException("Student profile not found, set up your profile first"));
    }

    private StudentProfileEntity findProfileByUsername(String username) {
        return studentProfileRepository.findByDisplayName(username)
                .orElseThrow(() -> new StudentProfileNotFoundException("Student profile not found, set up your profile first"));
    }
}