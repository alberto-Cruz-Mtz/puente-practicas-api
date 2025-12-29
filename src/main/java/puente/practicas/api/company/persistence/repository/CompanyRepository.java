package puente.practicas.api.company.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import puente.practicas.api.company.persistence.entity.CompanyEntity;

import java.util.UUID;

public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {

    boolean existsByRfc(String rfc);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);
}
