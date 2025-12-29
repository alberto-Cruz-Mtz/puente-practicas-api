package puente.practicas.api.profile.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import puente.practicas.api.company.persistence.entity.CompanyEntity;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@SuperBuilder
@Entity
@Table(name = "recruiter_profile")
@PrimaryKeyJoinColumn(name = "id")
public class RecruiterProfileEntity extends ProfileEntity {

    @ManyToOne(cascade = CascadeType.REMOVE, targetEntity = CompanyEntity.class)
    @JoinColumn(name = "company_id", nullable = false)
    private CompanyEntity company;
}
