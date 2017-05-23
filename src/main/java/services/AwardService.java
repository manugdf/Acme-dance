
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Award;
import repositories.AwardRepository;

@Service
@Transactional
public class AwardService {

	@Autowired
	private AwardRepository		awardRepository;
	@Autowired
	private DanceSchoolService	danceSchoolService;


	// Constructor
	public AwardService() {
		super();
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

		return this.awardRepository.save(award);
	}

	public Collection<Award> findAwardBySchool(final int schoolId) {

		return this.danceSchoolService.findOne(schoolId).getAwards();
	}

	//Other methods
	public Collection<Award> awardsByCompetition(int competitionId){
		return awardRepository.awardsByCompetition(competitionId);
	}

}
