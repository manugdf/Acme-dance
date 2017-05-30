
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Alumn;
import services.AlumnService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU35 extends AbstractTest {

	//CU35 - Un usuario logueado como alumno accede a una vista donde ve sus
	//datos básicos y decide modificar sus datos básicos además de la tarjeta
	//de crédito.

	@Autowired
	private AlumnService alumnService;


	@Test
	public void editprofile() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "Manolo"
			}, {
				"alumn2", null, "nombre"
			}, {
				"alumn2", IllegalArgumentException.class, "correo"
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
			final Alumn cp = this.alumnService.findByPrincipal();
			if (dato == "nombre")
				cp.setName(dato);
			if (dato == "correo")
				cp.setEmail(null);
			Assert.isTrue(cp.getEmail() != null);
			this.alumnService.save(cp);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
