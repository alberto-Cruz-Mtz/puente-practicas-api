package puente.practicas.api.offer.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import puente.practicas.api.offer.presentation.dto.OfferDetailsResponse;
import puente.practicas.api.offer.presentation.dto.OfferFilter;
import puente.practicas.api.offer.presentation.dto.OfferListResponse;
import puente.practicas.api.offer.presentation.dto.OfferSaveRequest;
import puente.practicas.api.offer.service.interfaces.OfferService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {

    private final OfferService offerService;

    @GetMapping
    public ResponseEntity<OfferListResponse> getAllOffers(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            OfferFilter filter
    ) {
        var response = offerService.getAllOffers(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-company")
    public ResponseEntity<OfferListResponse> getAllOffersByCompany(
            @RequestParam(value = "companyId") UUID companyId,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        var response = offerService.getOffersByCompanyId(companyId, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{offerId}")
    public ResponseEntity<OfferDetailsResponse> getOfferById(@PathVariable UUID offerId) {
        var response = offerService.getOfferById(offerId);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid OfferSaveRequest request) {
        UUID createdOfferId = offerService.publishOffer(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{offerId}")
                .buildAndExpand(createdOfferId)
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED).header(HttpHeaders.LOCATION, location.toString()).build();
    }

    @PutMapping("/{offerId}")
    public ResponseEntity<Void> update(@PathVariable UUID offerId, @RequestBody @Valid OfferSaveRequest request) {
        offerService.updateOffer(offerId, request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).header(HttpHeaders.LOCATION, location.toString()).build();
    }
}