package puente.practicas.api.profile.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.auth.persistence.entity.UserEntity;
import puente.practicas.api.auth.service.interfaces.UserService;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.company.service.interfaces.CompanyService;
import puente.practicas.api.profile.persistence.entity.ProfileEntity;
import puente.practicas.api.profile.persistence.entity.RecruiterProfileEntity;
import puente.practicas.api.profile.persistence.repository.RecruiterProfileRepository;
import puente.practicas.api.profile.presentation.dto.ProfileSummaryResponse;
import puente.practicas.api.profile.presentation.dto.RecruiterProfileRequest;
import puente.practicas.api.profile.service.interfaces.ProfileService;
import puente.practicas.api.profile.service.interfaces.RecruiterProfileService;
import puente.practicas.api.profile.util.ProfileMapper;

@Service
@RequiredArgsConstructor
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    private final ProfileService profileService;
    private final RecruiterProfileRepository repository;

    private final UserService userService;
    private final CompanyService companyService;

    private final ProfileMapper profileMapper;

    @Override
    @Transactional
    public ProfileSummaryResponse createRecruiterProfile(RecruiterProfileRequest request, String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);
        CompanyEntity company = companyService.getCompanyById(request.companyId());

        profileService.ensureProfileNotExists(user.getUid());
        profileService.ensureDisplayNameNotInUse(request.username());

        RecruiterProfileEntity profile = RecruiterProfileEntity.builder()
                .user(user)
                .displayName(request.username())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .avatarUrl(request.avatarUrl())
                .company(company)
                .build();

        RecruiterProfileEntity savedProfile = repository.save(profile);
        return profileMapper.toProfileSummaryResponse(savedProfile);
    }

    @Override
    @Transactional(readOnly = true)
    public ProfileSummaryResponse getRecruiterProfile(String userEmail) {
        UserEntity user = userService.findByEmail(userEmail);
        ProfileEntity profile = profileService.findProfileById(user.getUid());
        return profileMapper.toProfileSummaryResponse(profile);
    }
}
