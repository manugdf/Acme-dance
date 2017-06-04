
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.AwardRepository;
import services.AwardService;
import services.CompetitionService;
import services.DanceSchoolService;
import utilities.AbstractTest;
import domain.Award;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU44 extends AbstractTest {

	@Autowired
	private CompetitionService	competitionService;

	@Autowired
	private DanceSchoolService	danceSchoolService;

	@Autowired
	private AwardService		awardService;

	@Autowired
	private AwardRepository		awardRepository;


	//CU-44 Un usuario logueado como organizador y le crea un premio asociado,
	//concediéndole dicho premio a una de las escuelas apuntadas.

	@Test
	public void giveAward() {
		final Object[][] testingData = {
			{
				"planner1", null, "winnerName", 1, "prize", 110, 114
			}, {
				"planner1", null, "winnerName", 1, "prize", 112, 116
			}, {
				//se da como ganador a una escuela que no está inscrita en la competicion
				"planner1", IllegalArgumentException.class, "winnerName", 2, "prize", 110, 115
			}, {
				null, IllegalArgumentException.class, "winnerName", 2, "prize", 110, 115
			}, {
				"manager1", IllegalArgumentException.class, "winnerName", 2, "prize", 110, 115
			}, {
				"alumn1", IllegalArgumentException.class, "winnerName", 2, "prize", 110, 115
			}, {
				"planner1", ConstraintViolationException.class, "", 1, "prize", 112, 116
			}, {
				"planner1", ConstraintViolationException.class, "winnerName", -1, "prize", 112, 116
			}, {
				"planner1", ConstraintViolationException.class, "winnerName", 1, "", 112, 116
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.giveAwardTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (int) testingData[i][3], (String) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6]);
	}
	protected void giveAwardTemplate(final String username, final Class<?> expected, final String winnerName, final int place, final String prize, final int competitionId, final int danceSchoolId) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			final Award a = this.awardService.create();

			a.setWinnerName(winnerName);
			a.setPlace(place);
			a.setPrize(prize);
			a.setCompetition(this.competitionService.findOne(competitionId));
			a.setDanceSchool(this.danceSchoolService.findOne(danceSchoolId));

			this.awardService.save(a);
			this.awardRepository.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
