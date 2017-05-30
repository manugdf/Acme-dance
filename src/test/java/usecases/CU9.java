
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceSchool;
import services.DanceSchoolService;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU9 extends AbstractTest {

	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private ManagerService		managerService;


	//CU9 - Un usuario logueado como empresario accede a una vista donde ve
	//la lista de las escuelas que han sido enviadas por él (MIS ESCUELAS)
	//y decide modificar los datos básicos de una que haya sido aceptada por el administrador.
	//Esto incluye modificar la descripción, teléfono, localización e imagen.

	@Test
	public void modifyMySchools() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "Baile basico"
			}, {
				"manager1", null, "Calle del avetardo 4"
			}, {
				"manager2", null, "Descripción"
			}, {
				"planner1", NullPointerException.class, "sarmiento"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.modifyMySchoolTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void modifyMySchoolTemplate(final String username, final Class<?> expected, final String word) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			DanceSchool dc = new DanceSchool();
			final Collection<DanceSchool> dcs = this.managerService.findByPrincipal().getDanceSchools();
			for (final DanceSchool d : dcs)
				dc = d;
			if (word == "Calle del avetardo 4")
				dc.getLocation().setAddress(word);
			if (word == "Descripción")
				dc.setDescription("Escuela divertida");
			this.danceSchoolService.save(dc);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
