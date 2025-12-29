package puente.practicas.api.offer.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import puente.practicas.api.offer.persistence.model.ApplicationStatus;
import puente.practicas.api.profile.persistence.entity.StudentProfileEntity;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "application")
public class ApplicationEntity {

    @EmbeddedId
    private ApplicationId id;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = StudentProfileEntity.class, fetch = FetchType.LAZY)
    @MapsId(value = "studentProfileId")
    @JoinColumn(name = "student_id", nullable = false)
    private StudentProfileEntity studentProfile;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = OfferEntity.class, fetch = FetchType.LAZY)
    @MapsId(value = "offerId")
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "status", nullable = false)
    private ApplicationStatus status = ApplicationStatus.PENDING;

    @CreatedDate
    @Column(name = "applied_at", nullable = false)
    private Instant appliedAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}