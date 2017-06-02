package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Fee;
import services.FeeService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU40 extends AbstractTest{
	
	@Autowired
	private FeeService feeService;


	//CU40 - Un usuario logueado como admin accede a una vista donde ve la cuota mensual que debe pagar cada empresario por tener un banner aceptado 
	// en la página principal y decide modificarla



	@Test
	public void editFeeTest() {
		final Object[][] testingData = {
			{
				"admin", null
			}
			,{
				"manager1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}, {
				"teacher1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editFeeTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void editFeeTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Fee fee= new Fee();
			fee.setManagerAmount(1994.02);
			feeService.editFee(fee);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
