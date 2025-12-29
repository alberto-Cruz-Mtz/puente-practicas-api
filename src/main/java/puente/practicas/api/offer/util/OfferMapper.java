package puente.practicas.api.offer.util;

import org.springframework.stereotype.Component;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.offer.persistence.model.WorkModality;
import puente.practicas.api.offer.presentation.dto.OfferDetailsResponse;
import puente.practicas.api.offer.presentation.dto.OfferSaveRequest;
import puente.practicas.api.offer.presentation.dto.OfferSummary;

@Component
public class OfferMapper {

    public OfferEntity toOfferEntity(OfferSaveRequest request) {
        WorkModality modality = this.mapModality(request.modality());
        return OfferEntity.builder()
                .title(request.title())
                .description(request.description())
                .responsibilities(request.responsibilities())
                .requiredSkills(request.requirements())
                .learningOutcomes(request.learning())
                .duration(request.duration())
                .modality(modality)
                .location(request.location())
                .vacancies(request.vacancies())
                .isPaid(request.isPaid())
                .build();
    }

    public OfferSummary toOfferSummary(OfferEntity offer) {
        String companyName = offer.getCompany().getTradeName();
        String companyLogoUrl = offer.getCompany().getLogoUrl();

        return new OfferSummary(
                offer.getId(),
                offer.getTitle(),
                companyName,
                companyLogoUrl,
                offer.getLocation(),
                offer.getDuration(),
                offer.getModality().name(),
                offer.getCreatedAt()
        );
    }

    public OfferDetailsResponse toOfferDetails(OfferEntity offer) {
        String companyName = offer.getCompany().getTradeName();
        String companyLogoUrl = offer.getCompany().getLogoUrl();
        String modality = this.mapModality(offer.getModality());

        return new OfferDetailsResponse(
                offer.getId(),
                offer.getTitle(),
                offer.getDescription(),
                offer.getRequiredSkills(),
                offer.getResponsibilities(),
                companyName,
                companyLogoUrl,
                offer.getLocation(),
                offer.getDuration(),
                modality,
                offer.isPaid(),
                offer.getVacancies(),
                offer.getCreatedAt()
        );
    }

    private WorkModality mapModality(String modality) {
        return switch (modality.toLowerCase()) {
            case "onsite" -> WorkModality.ONSITE;
            case "remote" -> WorkModality.REMOTE;
            case "hybrid" -> WorkModality.HYBRID;
            default -> throw new IllegalArgumentException("Invalid modality: " + modality);
        };
    }

    private String mapModality(WorkModality modality) {
        return switch (modality) {
            case ONSITE -> "onsite";
            case REMOTE -> "remote";
            case HYBRID -> "hybrid";
        };
    }

    public void updateOfferEntityFromRequest(OfferSaveRequest request, OfferEntity existingOffer) {
        existingOffer.setTitle(request.title());
        existingOffer.setDescription(request.description());
        existingOffer.setResponsibilities(request.responsibilities());
        existingOffer.setRequiredSkills(request.requirements());
        existingOffer.setLearningOutcomes(request.learning());
        existingOffer.setDuration(request.duration());
        existingOffer.setModality(this.mapModality(request.modality()));
        existingOffer.setLocation(request.location());
        existingOffer.setVacancies(request.vacancies());
        existingOffer.setPaid(request.isPaid());
    }
}
