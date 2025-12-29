package puente.practicas.api.offer.service.interfaces;

import org.springframework.data.domain.Pageable;
import puente.practicas.api.offer.presentation.dto.OfferDetailsResponse;
import puente.practicas.api.offer.presentation.dto.OfferFilter;
import puente.practicas.api.offer.presentation.dto.OfferListResponse;
import puente.practicas.api.offer.presentation.dto.OfferSaveRequest;

import java.util.UUID;

public interface OfferService {

    UUID publishOffer(OfferSaveRequest request);

    void updateOffer(UUID offerId, OfferSaveRequest request);

    OfferListResponse getAllOffers(OfferFilter filter, Pageable pageable);

    OfferListResponse getOffersByCompanyId(UUID companyId, Pageable pageable);

    OfferDetailsResponse getOfferById(UUID offerId);

}
