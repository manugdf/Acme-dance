package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Manager;
import repositories.ManagerRepository;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ManagerService {
	
	@Autowired
	private ManagerRepository managerRepository;
	
	// Constructor
	public ManagerService() {
		super();
	}
	
	// Simple CRUD methods

	public Manager findOne(final int managerId) {
		Manager result=this.managerRepository.findOne(managerId);
		return result;
	}
	
	public Manager save(final Manager manager) {
		Assert.notNull(manager);
		final Manager managerSaved = this.managerRepository.save(manager);
		return managerSaved;
	}
	
	
	public Manager findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Manager manager = this.managerRepository.findByPrincipal(userAccount.getId());
		Assert.isTrue(manager.getUserAccount().equals(userAccount));
		return manager;
	}
}
