package puente.practicas.api.offer.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.offer.persistence.entity.ApplicationEntity;
import puente.practicas.api.offer.persistence.entity.ApplicationId;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;
import puente.practicas.api.offer.persistence.repository.ApplicationRepository;
import puente.practicas.api.offer.presentation.dto.ApplicantsResponse;
import puente.practicas.api.offer.presentation.dto.ApplicantListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationRequest;
import puente.practicas.api.offer.presentation.dto.StatusChangeRequest;
import puente.practicas.api.offer.service.exception.ApplicationAlreadyExistsException;
import puente.practicas.api.offer.service.exception.ApplicationNotFoundException;
import puente.practicas.api.offer.service.exception.InvalidApplicationStateException;
import puente.practicas.api.offer.service.interfaces.ApplicationService;
import puente.practicas.api.offer.service.interfaces.OfferService;
import puente.practicas.api.offer.util.ApplicationMapper;
import puente.practicas.api.offer.util.PaginationMapper;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;
import puente.practicas.api.profile.service.interfaces.StudentProfileService;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    private final StudentProfileService studentProfileService;
    private final OfferService offerService;

    private final PaginationMapper paginationMapper;
    private final ApplicationMapper applicationMapper;

    @Override
    @Transactional
    public void applyToOffer(UUID offerId, UUID studentId) {
        ApplicationId applicationId = new ApplicationId(studentId, offerId);
        this.ensureApplicationDoesNotExist(applicationId);

        StudentProfileEntity studentProfile = studentProfileService.findStudentById(applicationId.getStudentProfileId());
        OfferEntity offer = offerService.findOfferById(applicationId.getOfferId());

        ApplicationEntity application = ApplicationEntity.builder()
                .offer(offer)
                .studentProfile(studentProfile)
                .build();

        applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicantListResponse getApplicantsByOfferId(UUID offerId, Pageable pageable) {
        OfferEntity offer = offerService.findOfferById(offerId);
        Slice<ApplicationEntity> applications = applicationRepository.findAllByOffer(offer, pageable);

        var applicants = applications.map(applicationMapper::buildApplicantsResponse).toList();
        var pagination = paginationMapper.toPagination(applications);

        return new ApplicantListResponse(applicants, pagination);
    }

    @Override
    @Transactional
    public void withdrawApplication(UUID offerId, UUID studentId) {
        ApplicationId id = new ApplicationId(studentId, offerId);
        ApplicationEntity application = this.findApplicationById(id);

        application.setStatus(ApplicationStatus.WITHDRAWN);
        applicationRepository.save(application);
    }

    @Override
    @Transactional(readOnly = true)
    public ApplicationListResponse getApplicationsByStudentId(UUID studentId, Pageable pageable) {
        StudentProfileEntity studentProfile = studentProfileService.findStudentById(studentId);
        Slice<ApplicationEntity> applications = applicationRepository.findAllByStudentProfile(studentProfile, pageable);
        var list = applications.map(applicationMapper::buildApplicationResponse).toList();

        return new ApplicationListResponse(list, paginationMapper.toPagination(applications));
    }

    @Override
    @Transactional
    public void updateApplicationStatus(StatusChangeRequest request) {
        ApplicationId id = new ApplicationId(request.studentId(), request.offerId());
        ApplicationEntity application = this.findApplicationById(id);

        application.setStatus(request.newStatus());
        applicationRepository.save(application);
    }

    private ApplicationEntity findApplicationById(ApplicationId id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException("Not found application in this offer for this student."));
    }

    private void ensureApplicationDoesNotExist(ApplicationId applicationId) {
        Optional<ApplicationEntity> existingApplication = applicationRepository.findById(applicationId);

        if (existingApplication.isEmpty()) return;

        ApplicationStatus status = existingApplication.get().getStatus();

        if (status == ApplicationStatus.WITHDRAWN) {
            throw new InvalidApplicationStateException("Cannot reapply to an offer after withdrawing the application.");
        }

        throw new ApplicationAlreadyExistsException("Application already exists for this student and offer.");
    }
}
