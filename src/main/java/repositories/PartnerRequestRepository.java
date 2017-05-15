
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PartnerRequest;

@Repository
public interface PartnerRequestRepository extends JpaRepository<PartnerRequest, Integer> {

}
