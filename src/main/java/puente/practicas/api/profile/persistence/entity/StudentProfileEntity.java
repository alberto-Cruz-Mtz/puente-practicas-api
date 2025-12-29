package puente.practicas.api.profile.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
@Entity
@Table(name = "student_profile")
@PrimaryKeyJoinColumn(name = "id")
public class StudentProfileEntity extends ProfileEntity {

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "cv_url", length = 255)
    private String cvUrl;

    @Column(name = "portfolio_url", length = 255)
    private String portfolioUrl;
}
