
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.PartnerInvitation;

@Repository
public interface PartnerInvitationRepository extends JpaRepository<PartnerInvitation, Integer> {

}
