
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.CompetitionPlanner;
import services.CompetitionPlannerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU45 extends AbstractTest {

	//CU45- un usuario logueado como organizador accede a la vista
	//donde ve sus datos básicos y le permite modificarlos.

	@Autowired
	private CompetitionPlannerService competitionPlannerService;


	@Test
	public void editprofile() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "Manolo"
			}, {
				"planner2", null, "nombre"
			}, {
				"planner1", IllegalArgumentException.class, "correo"
			}, {
				"planner1", ConstraintViolationException.class, "manolo"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.editProfileTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void editProfileTemplate(final String username, final Class<?> expected, final String dato) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final CompetitionPlanner cp = this.competitionPlannerService.findByPrincipal();
			if (dato == "nombre")
				cp.setName(dato);
			if (dato == "correo")
				cp.setEmail(null);
			Assert.isTrue(cp.getEmail() != null);
			this.competitionPlannerService.save(cp);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
