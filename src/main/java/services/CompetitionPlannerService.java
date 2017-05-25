
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Competition;
import domain.CompetitionPlanner;
import domain.Message;
import forms.CompetitionPlannerForm;
import repositories.CompetitionPlannerRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class CompetitionPlannerService {

	@Autowired
	private CompetitionPlannerRepository competitionPlannerRepository;


	public CompetitionPlanner create() {
		final CompetitionPlanner res = new CompetitionPlanner();
		final UserAccount userAccount = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("COMPETITIONPLANNER");
		userAccount.addAuthority(a);
		res.setUserAccount(userAccount);
		res.setCompetitions(new ArrayList<Competition>());
		res.setMessagesReceived(new ArrayList<Message>());
		res.setMessagesSended(new ArrayList<Message>());
		return res;
	}

	public Collection<CompetitionPlanner> findAll() {
		return this.competitionPlannerRepository.findAll();
	}

	public CompetitionPlanner save(final CompetitionPlanner c) {
		return this.competitionPlannerRepository.save(c);
	}

	public void delete(final CompetitionPlanner c) {
		this.competitionPlannerRepository.delete(c);
	}

	public CompetitionPlanner findOne(final int competitionPlannerId) {
		return this.competitionPlannerRepository.findOne(competitionPlannerId);
	}

	public CompetitionPlanner reconstruct(final CompetitionPlannerForm c) {

		final CompetitionPlanner res = this.create();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		res.setName(c.getName());
		res.setSurname(c.getSurname());
		res.setEmail(c.getEmail());
		res.setPhone(c.getPhone());
		res.getUserAccount().setUsername(c.getUsername());
		res.getUserAccount().setPassword(encoder.encodePassword(c.getPassword(), null));
		res.setCompanyName(c.getCompanyName());
		res.setPicture(c.getPicture());

		return res;

	}

	public CompetitionPlanner findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final CompetitionPlanner cp = this.competitionPlannerRepository.findByPrincipal(userAccount.getId());
		Assert.isTrue(cp.getUserAccount().equals(userAccount));
		return cp;

	}

	public CompetitionPlannerForm reconstructForm(final CompetitionPlanner logged, final CompetitionPlannerForm c) {

		c.setCompanyName(logged.getCompanyName());
		c.setEmail(logged.getEmail());
		c.setName(logged.getName());
		c.setPhone(logged.getPhone());
		c.setPicture(logged.getPicture());
		c.setSurname(logged.getSurname());
		c.setUsername(logged.getUserAccount().getUsername());

		return c;
	}

	public void reconstructEdit(final CompetitionPlanner logged, final CompetitionPlannerForm c) {
		if ((c.getNewPassword().length() > 0 && c.getRepeatNewPassword().length() > 0 && c.getNewPassword().equals(c.getRepeatNewPassword()))) {
			final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
			logged.getUserAccount().setPassword(encoder.encodePassword(c.getNewPassword(), null));
		}
		logged.setCompanyName(c.getCompanyName());
		logged.setEmail(c.getEmail());
		logged.setName(c.getName());
		logged.setPhone(c.getPhone());
		logged.setPicture(c.getPicture());
		logged.setSurname(c.getSurname());
		logged.getUserAccount().setUsername(c.getUsername());
	}
}
