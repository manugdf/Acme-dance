
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.PartnerRequest;
import repositories.PartnerRequestRepository;

@Service
@Transactional
public class PartnerRequestService {

	@Autowired
	private PartnerRequestRepository	partnerRequestRepository;

	@Autowired
	private AlumnService				alumnService;


	// Constructor
	public PartnerRequestService() {
		super();
	}

	// Simple CRUD methods

	public PartnerRequest create() {
		final PartnerRequest res = new PartnerRequest();
		res.setAlumn(this.alumnService.findByPrincipal());
		return res;
	}

	public Collection<PartnerRequest> findAll() {
		return this.partnerRequestRepository.findAll();
	}

	public PartnerRequest findOne(final int id) {
		PartnerRequest result;
		result = this.partnerRequestRepository.findOne(id);
		return result;
	}

	public PartnerRequest save(final PartnerRequest partnerRequest) {

		return this.partnerRequestRepository.save(partnerRequest);
	}

	public void delete(final PartnerRequest pr) {
		this.partnerRequestRepository.delete(pr);
	}

}
