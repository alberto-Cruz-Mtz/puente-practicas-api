package puente.practicas.api.profile.util;

import org.springframework.stereotype.Component;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.profile.persistence.entity.ProfileEntity;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.presentation.dto.ProfileDetailsResponse;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.StudentProfileRequest;

@Component
public class ProfileMapper {

    public ProfileSummaryResponse toProfileSummaryResponse(ProfileEntity profile) {
        return new ProfileSummaryResponse(
                profile.getDisplayName(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getAvatarUrl()
        );
    }

    public ProfileDetailsResponse toProfileDetailsResponse(StudentProfileEntity studentProfile) {
        return new ProfileDetailsResponse(
                studentProfile.getDisplayName(),
                studentProfile.getFirstName(),
                studentProfile.getLastName(),
                studentProfile.getBiography(),
                studentProfile.getAvatarUrl(),
                studentProfile.getPortfolioUrl(),
                studentProfile.getCvUrl());
    }

    public StudentProfileEntity toStudentProfileEntity(StudentProfileRequest request, UserEntity user) {
        return StudentProfileEntity.builder()
                .user(user)
                .displayName(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .avatarUrl(request.avatarUrl())
                .cvUrl(request.cvUrl())
                .portfolioUrl(request.portfolioUrl())
                .biography(request.biography())
                .build();
    }
}