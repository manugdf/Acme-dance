
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Banner;
import domain.CreditCard;
import domain.DanceSchool;
import domain.Manager;
import domain.Message;
import domain.Teacher;
import forms.ManagerForm;
import repositories.ManagerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ManagerService {

	@Autowired
	private ManagerRepository managerRepository;
	
	@Autowired
	private FeeService feeService;
	@Autowired
	private Validator validator;


	// Constructor
	public ManagerService() {
		super();
	}

	// Simple CRUD methods

	public Manager create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.MANAGER);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		final Manager res = new Manager();
		res.setUserAccount(userAccount);
		res.setDanceSchools(new ArrayList<DanceSchool>());
		res.setBanner(new ArrayList<Banner>());
		res.setTeachers(new ArrayList<Teacher>());
		res.setMessagesReceived(new ArrayList<Message>());
		res.setMessagesSended(new ArrayList<Message>());
		res.setFee(0.0);

		return res;
	}

	public Manager modify(final Manager manager) {
		Assert.isTrue(manager.getId() == this.findByPrincipal().getId());
		return this.managerRepository.saveAndFlush(manager);
	}

	public Manager findOne(final int managerId) {
		final Manager result = this.managerRepository.findOne(managerId);
		return result;
	}

	public Collection<Manager> findAll() {
		return this.managerRepository.findAll();
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

	public Boolean LoggedIsManager() {
		Boolean res = false;
		try {
			final Authority aut = new Authority();
			aut.setAuthority(Authority.MANAGER);

			res = LoginService.getPrincipal().getAuthorities().contains(aut);
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}

	public void register(Manager manager) {
		Assert.notNull(manager);
		UserAccount userAccount;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = manager.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		manager.setUserAccount(userAccount);
		manager = this.save(manager);

		this.save(manager);
	}

	public ManagerForm reconstructForm(final Manager manager) {
		final ManagerForm managerForm = new ManagerForm();
		managerForm.setAcceptTerms(true);
		managerForm.setManagerId(manager.getId());
		managerForm.setUsername(manager.getUserAccount().getUsername());
		managerForm.setName(manager.getName());
		managerForm.setSurname(manager.getSurname());
		managerForm.setEmail(manager.getEmail());
		managerForm.setPhone(manager.getPhone());

		managerForm.setBrandName(manager.getCreditCard().getBrandName());
		managerForm.setCvvCode(manager.getCreditCard().getCvvCode());
		managerForm.setExpirationMonth(manager.getCreditCard().getExpirationMonth());
		managerForm.setExpirationYear(manager.getCreditCard().getExpirationYear());
		managerForm.setHolderName(manager.getCreditCard().getHolderName());
		managerForm.setNumber(manager.getCreditCard().getNumber());

		return managerForm;
	}

	public Manager reconstructEdit(final ManagerForm managerForm, final Manager manager) {

		manager.getUserAccount().setUsername(managerForm.getUsername());

		if ((managerForm.getNewpassword().length() > 0 && managerForm.getRepeatnewpassword().length() > 0 && managerForm.getNewpassword().equals(managerForm.getRepeatnewpassword()))) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			manager.getUserAccount().setPassword(encoder.encodePassword(managerForm.getNewpassword(), null));
		}
		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhone(managerForm.getPhone());

		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(managerForm.getBrandName());
		creditcard.setCvvCode(managerForm.getCvvCode());
		creditcard.setExpirationMonth(managerForm.getExpirationMonth());
		creditcard.setExpirationYear(managerForm.getExpirationYear());
		creditcard.setHolderName(managerForm.getHolderName());
		creditcard.setNumber(managerForm.getNumber());

		manager.setCreditCard(creditcard);
		return manager;
	}

	public Manager reconstruct(final ManagerForm managerForm, final BindingResult binding) {
		final Manager manager = this.create();
		if (managerForm.getUsername().length() > 0)
			manager.getUserAccount().setUsername(managerForm.getUsername());
		if (managerForm.getPassword().length() > 0)
			manager.getUserAccount().setPassword(managerForm.getPassword());
		manager.setName(managerForm.getName());
		manager.setSurname(managerForm.getSurname());
		manager.setEmail(managerForm.getEmail());
		manager.setPhone(managerForm.getPhone());

		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(managerForm.getBrandName());
		creditcard.setCvvCode(managerForm.getCvvCode());
		creditcard.setExpirationMonth(managerForm.getExpirationMonth());
		creditcard.setExpirationYear(managerForm.getExpirationYear());
		creditcard.setHolderName(managerForm.getHolderName());
		creditcard.setNumber(managerForm.getNumber());

		manager.setCreditCard(creditcard);
		return manager;
	}
	
	public Double getMonthTotalFee(){
		Manager manager=findByPrincipal();
		Assert.notNull(manager);
		int contador=0;
		for(Banner b : manager.getBanner()){
			if(b.getState().equals("ACCEPTED")){
				contador++;
			}
		}
		return contador*feeService.selectFee().getManagerAmount();	
	}
}
