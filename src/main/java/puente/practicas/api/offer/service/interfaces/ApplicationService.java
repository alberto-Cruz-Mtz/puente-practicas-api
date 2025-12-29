package puente.practicas.api.offer.service.interfaces;

import puente.practicas.api.offer.presentation.dto.ApplicantsResponse;
import puente.practicas.api.offer.presentation.dto.ApplyRequest;

import java.util.List;
import java.util.UUID;

public interface ApplicationService {

    void applyToOffer(ApplyRequest request);

    List<ApplicantsResponse> getApplicantsByOfferId(UUID offerId);

    void withdrawApplication(UUID applicationId);

    void updateApplicationStatus(UUID applicationId, String status);
}
