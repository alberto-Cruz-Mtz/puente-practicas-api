package puente.practicas.api.offer.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.offer.persistence.entity.ApplicationEntity;
import puente.practicas.api.offer.persistence.entity.ApplicationId;
import puente.practicas.api.offer.persistence.repository.ApplicationRepository;
import puente.practicas.api.offer.presentation.dto.ApplicantsResponse;
import puente.practicas.api.offer.presentation.dto.ApplyRequest;
import puente.practicas.api.offer.service.interfaces.ApplicationService;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.service.interfaces.StudentProfileService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentProfileService studentProfileService;

    @Override
    @Transactional
    public void applyToOffer(ApplyRequest request) {
        ApplicationId applicationId = new ApplicationId(request.studentId(), request.offerId());

        if (applicationRepository.existsById(applicationId)) {
            throw new IllegalArgumentException("Application already exists for this student and offer.");
        }

        StudentProfileEntity studentProfile = studentProfileService.findStudentById(applicationId.getStudentProfileId());
    }

    @Override
    public List<ApplicantsResponse> getApplicantsByOfferId(UUID offerId) {
        return List.of();
    }

    @Override
    public void withdrawApplication(UUID applicationId) {
    }

    @Override
    public void updateApplicationStatus(UUID applicationId, String status) {
    }
}