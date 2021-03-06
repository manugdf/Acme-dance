
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Banner;
import domain.CreditCard;
import domain.Manager;
import repositories.BannerRepository;

@Service
@Transactional
public class BannerService {

	@Autowired
	private BannerRepository	bannerRepository;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private FeeService			feeService;

	@Autowired
	private Validator			validator;

	@Autowired
	private ActorService		actorService;

	@Autowired
	private AdminService		adminService;


	// Constructor
	public BannerService() {
		super();
	}

	// Simple CRUD methods

	public Collection<Banner> findAll() {
		return this.bannerRepository.findAll();
	}

	public Banner findOne(final int id) {
		Banner result;
		result = this.bannerRepository.findOne(id);
		return result;
	}

	public Banner save(final Banner banner) {

		return this.bannerRepository.save(banner);
	}

	public Banner create() {
		final Banner res = new Banner();
		res.setState("PENDING");
		res.setManager(this.managerService.findByPrincipal());

		return res;
	}

	public Banner reconstruct(final Banner banner, final BindingResult binding) {
		Banner res = new Banner();

		if (banner.getId() == 0)
			res = banner;
		else {
			res.setUrl(banner.getUrl());
			res.setState(banner.getState());
		}
		this.validator.validate(res, binding);

		return res;
	}

	public Banner reconstructEdit(final Banner banner, final BindingResult bindingResult) {

		final Banner res = this.bannerRepository.findOne(banner.getId());

		res.setUrl(banner.getUrl());
		res.setState(banner.getState());
		this.validator.validate(banner, bindingResult);

		return res;
	}

	public Banner newBanner(Banner banner) {

		Assert.isTrue(this.managerService.LoggedIsManager());
		final Manager logged = this.managerService.findByPrincipal();
		Assert.isTrue(banner.getManager().equals(logged));
		final CreditCard c = banner.getManager().getCreditCard();
		Assert.isTrue(this.actorService.checkCreditCard(c));

		banner = this.save(banner);

		logged.setFee(logged.getFee() + this.feeService.selectFee().getManagerAmount());
		this.managerService.save(logged);

		return banner;
	}

	public Banner rejectBanner(final int id) {
		final boolean admin = this.adminService.isAdministrator();
		Assert.isTrue(admin == true);

		final Banner banner = this.findOne(id);
		banner.setState("REJECTED");
		return this.save(banner);
	}

	public Banner acceptBanner(final int id) {
		final boolean admin = this.adminService.isAdministrator();
		Assert.isTrue(admin == true);

		final Banner banner = this.findOne(id);
		banner.setState("ACCEPTED");
		return this.save(banner);
	}

	public Collection<Banner> findAllByManager(final int managerId) {
		return this.bannerRepository.findAllByManager(managerId);
	}
	public Collection<Banner> findAllPending() {
		return this.bannerRepository.findAllPending();
	}

	public Collection<Banner> findAllAccepted() {
		return this.bannerRepository.findAllAccepted();
	}

}
