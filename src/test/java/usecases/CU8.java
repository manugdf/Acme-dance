
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceSchool;
import domain.Location;
import services.DanceSchoolService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU8 extends AbstractTest {
	//CU8 - Un usuario logueado como empresario accede a
	//un formulario de envío de propuesta de nueva escuela, escribe los datos pertinentes (datos básicos de la escuela) y lo manda.

	@Autowired
	private DanceSchoolService danceSchoolService;


	@Test
	public void formSchool() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class
			}, {
				"manager1", null
			}, {
				"manager2", null
			}, {
				"admin", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.formSchoolTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void formSchoolTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final DanceSchool dc = this.danceSchoolService.create();
			dc.setDescription("Nueva escuela de baile");
			final Location location = new Location();
			location.setAddress("Calle almendras 2");
			location.setCity("Utrera");
			location.setProvince("Sevilla");
			dc.setLocation(location);
			dc.setName("Escuela Manolete");
			dc.setPhone("955862662");
			dc.setPicture("http://www.escuela.com/baile.jpg");

			this.danceSchoolService.newDanceSchool(dc);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
