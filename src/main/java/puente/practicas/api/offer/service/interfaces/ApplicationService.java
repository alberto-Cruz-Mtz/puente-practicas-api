package puente.practicas.api.offer.service.interfaces;

import org.springframework.data.domain.Pageable;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;
import puente.practicas.api.offer.presentation.dto.ApplicantListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationListResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationRequest;
import puente.practicas.api.offer.presentation.dto.StatusChangeRequest;

import java.util.UUID;

public interface ApplicationService {

    void applyToOffer(UUID offerId, UUID studentId);

    void withdrawApplication(UUID offerId, UUID studentId);

    ApplicationListResponse getApplicationsByStudentId(UUID studentId, Pageable pageable);

    ApplicantListResponse getApplicantsByOfferId(UUID offerId, Pageable pageable);

    void updateApplicationStatus(StatusChangeRequest request);
}
