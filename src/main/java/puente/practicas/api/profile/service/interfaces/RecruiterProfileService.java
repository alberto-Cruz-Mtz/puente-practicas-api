package puente.practicas.api.profile.service.interfaces;

import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.RecruiterProfileRequest;

public interface RecruiterProfileService {

    ProfileSummaryResponse createRecruiterProfile(RecruiterProfileRequest request, String userEmail);

    ProfileSummaryResponse getRecruiterProfile(String userEmail);
}
