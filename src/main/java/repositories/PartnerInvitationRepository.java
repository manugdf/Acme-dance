
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.PartnerInvitation;

@Repository
public interface PartnerInvitationRepository extends JpaRepository<PartnerInvitation, Integer> {

	@Query("select p from PartnerInvitation p where p.invitationReceiver.id = ?1")
	Collection<PartnerInvitation> findReceivedByAlumn(int id);

	@Query("select p from PartnerInvitation p where p.invitationSender.id = ?1")
	Collection<PartnerInvitation> findSendedByAlumn(int id);

}
