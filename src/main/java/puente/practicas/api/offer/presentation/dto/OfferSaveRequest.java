package puente.practicas.api.offer.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import puente.practicas.api.offer.persistence.model.WorkModality;

import java.util.UUID;

public record OfferSaveRequest(
        @NotBlank
        @Size(max = 200)
        String title,
        @NotBlank
        @Size(max = 5000)
        String description,
        @Size(max = 2000)
        String requirements,
        @Size(max = 2000)
        String responsibilities,
        @Size(max = 2000)
        String learning,
        @NotBlank
        @Size(max = 200)
        String location,
        @NotBlank
        String duration, // e.g., "6 months" "3 meses"
        @NotBlank
        @Size(max = 100)
        @Pattern(regexp = "ONSITE|REMOTE|HYBRID", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Modality must be one of the following: onsite, remote, hybrid")
        WorkModality modality,
        @Min(1)
        int vacancies,
        boolean isPaid,
        @NotNull
        UUID companyId
) {
}