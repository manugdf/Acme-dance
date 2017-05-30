
package usecases;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Alumn;
import domain.DanceClass;
import domain.Payment;
import services.AlumnService;
import services.DanceClassService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU29 extends AbstractTest {

	@Autowired
	private DanceClassService	dcService;

	@Autowired
	private AlumnService		alumnService;


	//CU29 - Un usuario logueado como alumno accede a la vista de las clases
	//a las que está apuntado, ve sus horarios, profesores, exámenes y materiales de interés,

	@Test
	public void listMyClasses() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class
			}, {
				"alumn1", null
			}, {
				"alumn2", null
			}, {
				"admin", NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.listMyClassesTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void listMyClassesTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Alumn alumn = this.alumnService.findByPrincipal();
			final Collection<DanceClass> dc = new ArrayList<DanceClass>();
			for (final Payment e : alumn.getPayments())
				dc.add(e.getDanceClass());
			Assert.notNull(dc);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
