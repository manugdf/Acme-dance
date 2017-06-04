
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.CompetitionPlannerRepository;
import services.CompetitionPlannerService;
import utilities.AbstractTest;
import domain.CompetitionPlanner;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU4 extends AbstractTest {

	@Autowired
	private CompetitionPlannerService		competitionPlannerService;

	@Autowired
	private CompetitionPlannerRepository	competitionRepository;


	//CU4 - Un usuario no logueado se registra como planificador de competiciones

	@Test
	public void register() {
		final Object[][] testingData = {
			{
				null, null, "username", "password", "name", "surname", "email@gmail.com", "655865231", "company", "http:\\picture.com"
			}, {
				"manager1", IllegalArgumentException.class, "username", "password", "name", "surname", "email@gmail.com", "phone", "company", "http:\\picture.com"
			}, {
				"alumn1", IllegalArgumentException.class, "username", "password", "name", "surname", "email@gmail.com", "phone", "company", "http:\\picture.com"
			}, {
				"planner1", IllegalArgumentException.class, "username", "password", "name", "surname", "email@gmail.com", "phone", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "", "password", "name", "surname", "email@gmail.com", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "", "name", "surname", "email@gmail.com", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "password", "", "surname", "email@gmail.com", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "password", "name", "", "email@gmail.com", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "password", "name", "surname", "email", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "password", "name", "surname", "", "655865231", "company", "http:\\picture.com"
			}, {
				null, ConstraintViolationException.class, "username", "password", "name", "surname", "em@gmail.com", "655865231", "company", "picture"
			}, {
				null, ConstraintViolationException.class, "username", "password", "name", "surname", "em@gmail.com", "1", "company", "picture"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.registerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6],
				(String) testingData[i][7], (String) testingData[i][8], (String) testingData[i][9]);
	}

	protected void registerTemplate(final String username, final Class<?> expected, final String user, final String password, final String name, final String surname, final String email, final String phone, final String company, final String picture) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CompetitionPlanner c = this.competitionPlannerService.create();
			c.getUserAccount().setUsername(user);
			c.getUserAccount().setPassword(password);
			c.setName(name);
			c.setSurname(surname);
			c.setEmail(email);
			c.setCompanyName(company);
			c.setPicture(picture);

			this.competitionPlannerService.register(c);
			this.competitionRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
