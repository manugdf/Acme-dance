
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Alumn;
import domain.PartnerRequest;
import services.AlumnService;
import services.PartnerRequestService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU32 extends AbstractTest {

	//CU32 - Un usuario logueado como alumno accede a una vista donde ve
	//todas las solicitudes de pareja que ha creado y decide crear una nueva.

	@Autowired
	private PartnerRequestService	partnerRequestService;

	@Autowired
	private AlumnService			alumnService;


	@Test
	public void createRequest() {
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
			this.createRequestTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void createRequestTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final PartnerRequest req = this.partnerRequestService.create();
			req.setDescription("Bailar y bailar");
			req.setDanceStyle("Chachacha");
			this.partnerRequestService.save(req);

			final Alumn alumn = this.alumnService.findByPrincipal();
			alumn.getPartnerRequests().contains(req);

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
