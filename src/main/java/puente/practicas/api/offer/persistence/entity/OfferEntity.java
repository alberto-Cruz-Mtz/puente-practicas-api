package puente.practicas.api.offer.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import puente.practicas.api.common.entity.AuditableEntity;
import puente.practicas.api.company.persistence.entity.CompanyEntity;
import puente.practicas.api.offer.persistence.model.WorkModality;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "offer")
public class OfferEntity extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "responsibilities", nullable = false, columnDefinition = "TEXT")
    private String responsibilities;

    @Column(name = "skills_required", nullable = false, columnDefinition = "TEXT")
    private String requiredSkills;

    @Column(name = "learning_outcomes", columnDefinition = "TEXT")
    private String learningOutcomes;

    @Column(name = "duration", nullable = false, length = 35)
    private String duration;

    @Enumerated(EnumType.STRING)
    @Column(name = "modality", nullable = false)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private WorkModality modality;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "vacancies", nullable = false)
    private int vacancies;

    @Column(name = "is_paid", nullable = false)
    private boolean isPaid = false;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = CompanyEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;
}
