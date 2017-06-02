package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.ManagerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU42 extends AbstractTest{
	
	@Autowired
	private ManagerService managerService;

	//CU42 - Un usuario logueado empresario puede acceder a una vista donde pulsar un botón para activar un proceso que les genere una factura 
	// con el pago total que debe realizar ese mes, que sea visible por el usuario

	@Test
	public void monthlyBillTest() {
		final Object[][] testingData = {
			{
				"manager1", null
			}
			,{
				"admin", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}, {
				"teacher1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.monthlyBillTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
			
		}
	}

	protected void monthlyBillTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double monthlyBill=managerService.getMonthTotalFee();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
