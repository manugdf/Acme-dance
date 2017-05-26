
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CompetitionRepository;
import domain.Award;
import domain.Competition;
import domain.DanceSchool;
import forms.SelectDanceSchoolForm;

@Service
@Transactional
public class CompetitionService {

	//Repository
	@Autowired
	private CompetitionRepository		competitionRepository;

	@Autowired
	private CompetitionPlannerService	competitionPlannerService;

	@Autowired
	private Validator					validator;


	//Services

	//Constructor
	public CompetitionService() {
		super();
	}

	public Competition create() {
		final Competition res = new Competition();

		return res;
	}

	//CRUD METHODS
	public Collection<Competition> findAll() {
		return this.competitionRepository.findAll();
	}

	public Competition findOne(final int id) {
		Competition result;
		result = this.competitionRepository.findOne(id);
		return result;
	}

	public Competition save(final Competition competition) {
		Assert.notNull(competition);

		return this.competitionRepository.save(competition);
	}

	public void delete(final Competition competition) {
		Assert.notNull(competition);
		this.competitionRepository.delete(competition);
	}

	public Competition signupDanceSchool(final SelectDanceSchoolForm selectDanceSchoolForm, final int competitionId) {
		final Competition competition = this.competitionRepository.findOne(competitionId);
		final DanceSchool danceSchool = selectDanceSchoolForm.getDanceSchool();
		//if(danceSchool!=null){
		competition.getDanceSchools().add(danceSchool);
		//}
		return competition;
	}

	public Competition reconstruct(final Competition compt, final BindingResult binding) {

		Competition res;

		if (compt.getId() == 0) {
			res = compt;
			res.setAwards(new ArrayList<Award>());
			res.setDanceSchools(new ArrayList<DanceSchool>());
			res.setCompetitionPlanner(this.competitionPlannerService.findByPrincipal());
		} else {

			res = this.findOne(compt.getId());
			res.setAlumnPerSchool(compt.getAlumnPerSchool());
			res.setDescription(compt.getDescription());
			res.setLimitInscription(compt.getLimitInscription());
			res.setLimitSchool(compt.getLimitSchool());
			res.setStartDate(compt.getStartDate());
			res.setPlace(compt.getPlace());
		}

		this.validator.validate(res, binding);

		return res;

	}
}
