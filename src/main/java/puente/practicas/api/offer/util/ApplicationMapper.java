package puente.practicas.api.offer.util;

import org.springframework.stereotype.Component;
import puente.practicas.api.offer.persistence.entity.ApplicationEntity;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;
import puente.practicas.api.offer.presentation.dto.ApplicantsResponse;
import puente.practicas.api.offer.presentation.dto.ApplicationResponse;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;

@Component
public class ApplicationMapper {

    public ApplicantsResponse buildApplicantsResponse(ApplicationEntity application) {
        StudentProfileEntity student = application.getStudentProfile();
        String fullName = student.getFirstName() + " " + student.getLastName();

        return new ApplicantsResponse(
                student.getId(),
                student.getAvatarUrl(),
                fullName,
                student.getCvUrl(),
                application.getStatus()
        );
    }

    public ApplicationResponse buildApplicationResponse(ApplicationEntity application) {
        OfferEntity offer = application.getOffer();

        return new ApplicationResponse(
                offer.getId(),
                offer.getTitle(),
                application.getStatus(),
                application.getAppliedAt()
        );
    }

    public ApplicationStatus mapStatus(String status) {
        return switch (status) {
            case "PENDING" -> ApplicationStatus.PENDING;
            case "ACCEPTED" -> ApplicationStatus.ACCEPTED;
            case "REJECTED" -> ApplicationStatus.REJECTED;
            case "WITHDRAWN" -> ApplicationStatus.WITHDRAWN;
            case "IN_REVIEW" -> ApplicationStatus.IN_REVIEW;
            default -> throw new IllegalArgumentException("Unknown status: " + status);
        };
    }
}
