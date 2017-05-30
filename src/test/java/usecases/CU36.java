
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceSchool;
import services.DanceSchoolService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU36 extends AbstractTest {

	//CU36 - Un usuario logueado como admin accede a una vista donde puede
	//ver las escuelas pendientes de aceptación, ve los datos de
	//cada una y decide rechazar o aceptar una de ellas.

	@Autowired
	private DanceSchoolService danceSchoolService;


	@Test
	public void aceptrejectSchool() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "ACCEPTED"
			}, {
				"admin", null, "ACCEPTED"
			}, {
				"admin", null, "REJECTED"
			}, {
				"planner1", IllegalArgumentException.class, "ACCEPTED"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.acceptrejectTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void acceptrejectTemplate(final String username, final Class<?> expected, final String dato) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			DanceSchool dc = new DanceSchool();
			for (final DanceSchool d : this.danceSchoolService.findAllPending())
				dc = d;

			if (dato == "ACCEPTED")
				this.danceSchoolService.acceptDanceSchool(dc.getId());
			if (dato == "REJECTED")
				this.danceSchoolService.rejectDanceSchool(dc.getId());

			this.unauthenticate();

			dc.setState("PENDING");
			this.danceSchoolService.save(dc);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
