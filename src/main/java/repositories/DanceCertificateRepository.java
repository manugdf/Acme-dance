
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.DanceCertificate;

import java.util.Collection;

@Repository
public interface DanceCertificateRepository extends JpaRepository<DanceCertificate, Integer> {
    @Query("select d from DanceCertificate d where d.alumn.id = ?1")
    public Collection<DanceCertificate> findDanceCertificatesByAlumn(int alumnId);

}
