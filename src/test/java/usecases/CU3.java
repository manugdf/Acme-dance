
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Alumn;
import domain.CreditCard;
import services.AlumnService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU3 extends AbstractTest {

	@Autowired
	private AlumnService alumnService;


	//CU3 - Un usuario no logueado se registra como alumno
	//y luego hace login con esa cuenta.

	@Test
	public void registerAlumn() {
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
			this.registerAlumnTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void registerAlumnTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			final Alumn alumn = this.alumnService.create();
			alumn.setName("Julian");
			alumn.getUserAccount().setUsername("julianlj");
			alumn.setSurname("Lopez");
			alumn.setEmail("julianlj@gmail.com");
			if (username == "correo")
				alumn.setEmail("2");
			Assert.isTrue(alumn.getEmail().contains("@"));
			alumn.setPhone("650190424");
			if (username == "telefono")
				alumn.setPhone(null);
			Assert.notNull(alumn.getPhone());
			final CreditCard creditcard = new CreditCard();
			creditcard.setBrandName("VISA");
			creditcard.setCvvCode(222);
			creditcard.setExpirationMonth(12);
			creditcard.setExpirationYear(2018);
			creditcard.setHolderName("Julian Lopez");
			if (username == "correcto")
				creditcard.setHolderName("Julian Lopez");
			creditcard.setNumber("4444010782452150");
			alumn.setCreditCard(creditcard);
			this.alumnService.register(alumn);
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
