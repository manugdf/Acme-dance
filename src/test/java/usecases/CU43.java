
package usecases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.CompetitionRepository;
import services.CompetitionService;
import utilities.AbstractTest;
import domain.Competition;
import domain.Location;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU43 extends AbstractTest {

	@Autowired
	private CompetitionService		competitionService;

	@Autowired
	private CompetitionRepository	competitionRepository;


	//CU-43 Un usuario logueado como organizadory
	//decide registrar una nueva competición a la que podrá apuntarse cualquier escuela del sistema.

	@Test
	public void createCompetition() {
		final Object[][] testingData = {
			{
				"planner1", null, "10/10/2018 21:00", "address", "description", 10, 2, "10/10/2018 21:00", "province", "city"
			}, {
				null, IllegalArgumentException.class, "10/10/2018 21:00", "address", "description", 10, 2, "10/10/2018 21:00", "province", "city"
			}, {
				"manager1", IllegalArgumentException.class, "10/10/2018 21:00", "address", "description", 10, 2, "10/10/2018 21:00", "province", "city"
			}, {
				"alumn1", IllegalArgumentException.class, "10/10/2018 21:00", "address", "description", 10, 2, "10/10/2018 21:00", "province", "city"
			}, {
				"planner1", ConstraintViolationException.class, "10/10/2018 21:00", "", "description", 10, 2, "10/10/2018 21:00", "province", "city"
			}, {
				"planner1", ConstraintViolationException.class, "10/10/2018 21:00", "address", "description", -1, 2, "10/10/2018 21:00", "province", "city"
			}, {
				"planner1", ConstraintViolationException.class, "10/10/2018 21:00", "address", "description", 10, -1, "10/10/2018 21:00", "province", "city"
			}, {
				"planner1", ConstraintViolationException.class, "10/10/2018 21:00", "address", "", 10, -1, "10/10/2018 21:00", "province", "city"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createCompetitionTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (int) testingData[i][5], (int) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9]);
	}
	protected void createCompetitionTemplate(final String username, final Class<?> expected, final String startDate, final String address, final String description, final int alumnPerSchool, final int limitSchool, final String limitInscription,
		final String province, final String city) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			final Competition c = this.competitionService.create();
			final Location place = new Location();
			place.setAddress(address);
			place.setCity(city);
			place.setProvince(province);

			c.setStartDate(formatter.parse(startDate));
			c.setDescription(description);
			c.setPlace(place);
			c.setLimitInscription(formatter.parse(limitInscription));
			c.setLimitSchool(limitSchool);
			c.setAlumnPerSchool(alumnPerSchool);

			this.competitionService.save(c);

			this.competitionRepository.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
