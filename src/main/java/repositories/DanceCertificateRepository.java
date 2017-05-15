
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.DanceCertificate;

@Repository
public interface DanceCertificateRepository extends JpaRepository<DanceCertificate, Integer> {

}
