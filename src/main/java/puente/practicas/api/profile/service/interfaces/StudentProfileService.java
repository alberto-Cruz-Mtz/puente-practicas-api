package puente.practicas.api.profile.service.interfaces;

import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;

import java.util.UUID;

public interface StudentProfileService {

    ProfileSummaryResponse createStudentProfile(StudentProfileRequest request, String userEmail);

    ProfileDetailsResponse getStudentProfileByEmail(String userEmail);

    ProfileDetailsResponse getStudentProfileByUsername(String username);

    StudentProfileEntity findStudentById(UUID id);
}
