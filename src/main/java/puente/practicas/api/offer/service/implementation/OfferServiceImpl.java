package puente.practicas.api.offer.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.company.service.interfaces.CompanyService;
import puente.practicas.api.offer.persistence.entity.OfferEntity;
import puente.practicas.api.offer.persistence.repository.OfferRepository;
import puente.practicas.api.offer.persistence.specification.OfferSpecification;
import puente.practicas.api.offer.presentation.dto.OfferDetailsResponse;
import puente.practicas.api.offer.presentation.dto.OfferFilter;
import puente.practicas.api.offer.presentation.dto.OfferListResponse;
import puente.practicas.api.offer.presentation.dto.OfferSaveRequest;
import puente.practicas.api.offer.presentation.dto.Pagination;
import puente.practicas.api.offer.service.exception.OfferNotFoundException;
import puente.practicas.api.offer.service.interfaces.OfferService;
import puente.practicas.api.offer.util.OfferMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final CompanyService companyService;
    private final OfferMapper offerMapper;

    @Override
    @Transactional
    public UUID publishOffer(OfferSaveRequest request) {
        var company = companyService.getCompanyById(request.companyId());

        OfferEntity offer = offerMapper.toOfferEntity(request);
        offer.setCompany(company);

        return offerRepository.save(offer).getId();
    }

    @Override
    @Transactional
    public void updateOffer(UUID offerId, OfferSaveRequest request) {
        OfferEntity existingOffer = this.findOfferById(offerId);

        if (!existingOffer.getCompany().getId().equals(request.companyId())) {
            throw new IllegalArgumentException("Offer does not belong to the specified company");
        }

        offerMapper.updateOfferEntityFromRequest(request, existingOffer);
        offerRepository.save(existingOffer);
    }

    @Override
    @Transactional(readOnly = true)
    public OfferListResponse getAllOffers(OfferFilter filter, Pageable pageable) {
        Specification<OfferEntity> specification = OfferSpecification.withFilter(filter);
        return this.buildOfferListResponse(offerRepository.findAll(specification, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public OfferListResponse getOffersByCompanyId(UUID companyId, Pageable pageable) {
        CompanyEntity company = companyService.getCompanyById(companyId);
        return this.buildOfferListResponse(offerRepository.findAllByCompanyOrderByCreatedAt(company, pageable));
    }

    @Override
    @Transactional(readOnly = true)
    public OfferDetailsResponse getOfferById(UUID offerId) {
        var offer = this.findOfferById(offerId);
        return offerMapper.toOfferDetails(offer);
    }

    @Override
    public OfferEntity findOfferById(UUID offerId) {
        return offerRepository.findById(offerId)
                .orElseThrow(() -> new OfferNotFoundException("Offer with the given ID does not exist"));
    }

    private OfferListResponse buildOfferListResponse(Slice<OfferEntity> offers) {
        var offerSummaries = offers.stream()
                .map(offerMapper::toOfferSummary)
                .toList();
        Pagination pagination = this.toPagination(offers);
        return new OfferListResponse(offerSummaries, pagination);
    }

    private Pagination toPagination(Slice<OfferEntity> offers) {
        return new Pagination(
                offers.getNumber(),
                offers.getSize(),
                offers.hasNext()
        );
    }
}