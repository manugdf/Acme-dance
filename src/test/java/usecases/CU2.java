
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.CreditCard;
import domain.Manager;
import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU2 extends AbstractTest {

	@Autowired
	private ManagerService managerService;


	//CU2 - Un usuario no logueado se registra como empresario
	//y luego hace login con esa cuenta.

	@Test
	public void registerManager() {
		final Object[][] testingData = {
			{
				null, null
			}, {
				"telefono", IllegalArgumentException.class
			}, {
				"correo", IllegalArgumentException.class
			}, {
				"correcto", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.registerManagerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void registerManagerTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Manager manager = this.managerService.create();
			manager.setName("Manolay");
			manager.getUserAccount().setUsername("manolay");
			manager.setSurname("Diaz");
			manager.setEmail("manolay@gmail.com");
			if (username == "correo")
				manager.setEmail("2");
			Assert.isTrue(manager.getEmail().contains("@"));
			manager.setPhone("659183896");
			if (username == "telefono")
				manager.setPhone(null);
			Assert.notNull(manager.getPhone());
			final CreditCard creditcard = new CreditCard();
			creditcard.setBrandName("VISA");
			creditcard.setCvvCode(222);
			creditcard.setExpirationMonth(12);
			creditcard.setExpirationYear(2018);
			creditcard.setHolderName("MANOLAY DIAZ SANCHEZ");
			if (username == "correcto")
				creditcard.setHolderName("MANUEL DIAZ SANCHEZ");
			creditcard.setNumber("4444010782452150");
			manager.setCreditCard(creditcard);
			this.managerService.register(manager);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
