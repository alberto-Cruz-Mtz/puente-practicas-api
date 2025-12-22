package puente.practicas.api.profile.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import puente.practicas.api.auth.persistence.entity.UserEntity;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "student_profile")
public class StudentProfileEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private UserEntity user;

    @Column(name = "name", nullable = false, length = 100)
    private String fullName;

    @Column(name = "cv_url", length = 255)
    private String cvUrl;

    @Column(name = "portfolio_url", length = 255)
    private String portfolioUrl;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
}
