
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompetitionPlannerRepository;
import security.Authority;
import security.UserAccount;
import domain.CompetitionPlanner;
import forms.CompetitionPlannerForm;

@Service
@Transactional
public class CompetitionPlannerService {

	@Autowired
	private CompetitionPlannerRepository	competitionPlannerRepository;

	@Autowired
	private Validator						validator;


	public CompetitionPlanner create() {
		final CompetitionPlanner res = new CompetitionPlanner();
		final UserAccount userAccount = new UserAccount();
		final Authority a = new Authority();
		a.setAuthority("COMPETITIONPLANNER");
		userAccount.addAuthority(a);
		res.setUserAccount(userAccount);
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

	public CompetitionPlanner reconstruct(final CompetitionPlannerForm c, final BindingResult binding) {

		final CompetitionPlanner res = this.create();
		res.setName(c.getName());
		res.setSurname(c.getSurname());
		res.setEmail(c.getEmail());
		res.setPhone(c.getPhone());
		//		final UserAccount userAccount = res.getUserAccount();
		//		userAccount.setUsername(c.getUsername());
		//		userAccount.setPassword(c.getPassword());
		res.setCompanyName(c.getCompanyName());
		res.setPicture(c.getPicture());

		this.validator.validate(res, binding);

		return res;

	}
}
