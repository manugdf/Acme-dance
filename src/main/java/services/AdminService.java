
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Admin;
import domain.Alumn;
import domain.Manager;
import domain.Teacher;
import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AdminService {

	//Repository------------------------------------------------------------------

	@Autowired
	private AdminRepository adminRepository;


	//CRUD Methods----------------------------------------------------------------

	public Admin findOne(final int id) {
		final Admin admin = this.adminRepository.findOne(id);
		Assert.notNull(admin);
		return admin;
	}

	//Other Methods--------------------------------------------------------------

	public Admin findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Admin admin = this.adminRepository.findByPrincipal(userAccount.getId());
		Assert.isTrue(admin.getUserAccount().equals(userAccount));
		return admin;
	}
	public void checkLoggedIsAdmin() {
		final Authority aut = new Authority();
		aut.setAuthority(Authority.ADMIN);
		Assert.isTrue(LoginService.getPrincipal().getAuthorities().contains(aut));
	}

	public Boolean isAdministrator() {
		Boolean res = false;
		try {
			final Authority aut = new Authority();
			aut.setAuthority(Authority.ADMIN);

			res = LoginService.getPrincipal().getAuthorities().contains(aut);
		} catch (final Exception e) {
			res = false;
		}

		return res;
	}
	
	//Dashboard
	public Collection<Manager> managerMoreDanceSchoolAccepted() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.managerMoreDanceSchoolAccepted();
	}
	
	public Integer acceptedDeniedRatio() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.acceptedDeniedRatio();
	}
	
	public Collection<Teacher> bestRating() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.bestRating();
	}
	
	public Double messagesUsersRatio() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.messagesUsersRatio();
	}
	
	public Collection<Actor> actorMoreMessageSend() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.actorMoreMessageSend();
	}
	
	public Collection<Alumn> alumnsMoreClasses() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.alumnsMoreClasses();
	}
	
	public Collection<Manager> managerMoreBannersAccepted() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.managerMoreBannersAccepted();
	}
	
	public Collection<Teacher> teachersOrderedByRatio() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.teachersOrderedByRatio();
	}
	
	public Object[] minAvgMaxClassesPerTeacher() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.minAvgMaxClassesPerTeacher();
	}
	
	public Double eventAverageDuration() {
		this.checkLoggedIsAdmin();
		return this.adminRepository.eventAverageDuration();
	}
}
