
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.PartnerInvitation;
import repositories.PartnerInvitationRepository;

@Service
@Transactional
public class PartnerInvitationService {

	@Autowired
	private PartnerInvitationRepository	partnerInvitationRepository;

	@Autowired
	private AlumnService				alumnService;


	// Constructor
	public PartnerInvitationService() {
		super();
	}

	// Simple CRUD methods

	public PartnerInvitation create(final int alumnId) {
		final PartnerInvitation res = new PartnerInvitation();
		res.setState("PENDING");
		res.setInvitationSender(this.alumnService.findByPrincipal());
		res.setInvitationReceiver(this.alumnService.findOne(alumnId));

		return res;
	}

	public Collection<PartnerInvitation> findAll() {
		return this.partnerInvitationRepository.findAll();
	}

	public PartnerInvitation findOne(final int id) {
		PartnerInvitation result;
		result = this.partnerInvitationRepository.findOne(id);
		return result;
	}

	public PartnerInvitation save(final PartnerInvitation partnerInvitation) {

		return this.partnerInvitationRepository.save(partnerInvitation);
	}

	public Collection<PartnerInvitation> findReceivedByAlumn(final int id) {
		return this.partnerInvitationRepository.findReceivedByAlumn(id);
	}

	public Collection<PartnerInvitation> findSendedByAlumn(final int id) {
		return this.partnerInvitationRepository.findSendedByAlumn(id);
	}

	public Collection<PartnerInvitation> findSendedAndAcceptedByAlumn(final int id) {
		return this.partnerInvitationRepository.findSendedAndAcceptedByAlumn(id);
	}
	public Collection<PartnerInvitation> findReceivedAndAcceptedByAlumn(final int id) {
		return this.partnerInvitationRepository.findReceivedAndAcceptedByAlumn(id);
	}

}
