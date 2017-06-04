
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AwardRepository;
import domain.Award;

@Service
@Transactional
public class AwardService {

	@Autowired
	private AwardRepository				awardRepository;
	@Autowired
	private DanceSchoolService			danceSchoolService;

	@Autowired
	private CompetitionPlannerService	competitionPlannerService;

	@Autowired
	private Validator					validator;


	// Constructor
	public AwardService() {
		super();
	}

	public Award create() {
		final Award res = new Award();

		return res;
	}

	// Simple CRUD methods

	public Collection<Award> findAll() {
		return this.awardRepository.findAll();
	}

	public Award findOne(final int id) {
		Award result;
		result = this.awardRepository.findOne(id);
		return result;
	}

	public Award save(final Award award) {

		Assert.isTrue(this.competitionPlannerService.LoggedIsCompetitionPlanner());
		Assert.isTrue(award.getCompetition().getDanceSchools().contains(award.getDanceSchool()));
		return this.awardRepository.save(award);
	}

	public Collection<Award> findAwardBySchool(final int schoolId) {

		return this.danceSchoolService.findOne(schoolId).getAwards();
	}

	//Other methods
	public Collection<Award> awardsByCompetition(final int competitionId) {
		return this.awardRepository.awardsByCompetition(competitionId);
	}

	public Award reconstruct(final Award award, final BindingResult binding) {
		Award res;

		if (award.getId() == 0)
			res = award;
		else {
			res = this.findOne(award.getId());
			res.setPlace(award.getPlace());
			res.setDanceSchool(award.getDanceSchool());
			res.setWinnerName(award.getWinnerName());
		}

		this.validator.validate(res, binding);

		return res;

	}

}
