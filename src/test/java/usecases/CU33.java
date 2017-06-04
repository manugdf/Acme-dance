
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Alumn;
import domain.PartnerInvitation;
import domain.PartnerRequest;
import services.AlumnService;
import services.DanceSchoolService;
import services.PartnerInvitationService;
import services.PartnerRequestService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU33 extends AbstractTest {

	@Autowired
	private PartnerRequestService		partnerRequestService;
	@Autowired
	private PartnerInvitationService	partnerInvitationService;
	@Autowired
	private AlumnService				alumnService;
	@Autowired
	private DanceSchoolService			danceSchoolService;


	//CU33 - Un usuario logueado como alumno accede a una vista donde se
	//muestran todas las solicitudes de parejas que hay abiertas y pertenezcan
	//a sus escuela apuntadas y decide mandar una invitación al
	//alumno de una de ellas.

	@Test
	public void invitate() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, 156
			}, {
				"alumn1", null, 156
			}, {
				"alumn2", null, 151
			}, {
				"admin", NullPointerException.class, 151
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.invitateTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void invitateTemplate(final String username, final Class<?> expected, final int id) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final PartnerRequest pr = this.partnerRequestService.findOne(id);
			PartnerInvitation pi = this.partnerInvitationService.create(pr.getAlumn().getId());
			pi = this.partnerInvitationService.save(pi);
			final Alumn alumn = this.alumnService.findByPrincipal();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
