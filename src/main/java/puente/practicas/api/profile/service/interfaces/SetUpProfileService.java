package puente.practicas.api.profile.service.interfaces;

import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;

public interface SetUpProfileService {

    ProfileResponse createStudentProfile(StudentProfileRequest request, String userEmail);

    ProfileDetailsResponse getStudentProfile(String userEmail);

}
