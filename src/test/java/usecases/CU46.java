
package usecases;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Manager;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU46 extends AbstractTest {
	//CU46 - Un usuario logueado como empresario accede a la vista donde ve
	//sus datos básicos y le permite modificarlos, incluida la tarjeta de crédito.

	@Autowired
	private ManagerService managerService;


	@Test
	public void editprofile() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "Manolo"
			}, {
				"manager1", null, "nombre"
			}, {
				"manager2", IllegalArgumentException.class, "card"
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
			final Manager cp = this.managerService.findByPrincipal();
			if (dato == "nombre")
				cp.setName(dato);
			if (dato == "card")
				cp.getCreditCard().setHolderName(null);
			Assert.isTrue(cp.getCreditCard().getHolderName() != null);
			this.managerService.save(cp);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
