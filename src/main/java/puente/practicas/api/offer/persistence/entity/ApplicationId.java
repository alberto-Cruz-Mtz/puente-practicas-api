package puente.practicas.api.offer.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
@Embeddable
public class ApplicationId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "student_id", nullable = false)
    private UUID studentProfileId;

    @Column(name = "offer_id", nullable = false)
    private UUID offerId;
}
