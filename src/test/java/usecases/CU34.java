
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Alumn;
import domain.PartnerInvitation;
import services.AlumnService;
import services.PartnerInvitationService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU34 extends AbstractTest {

	@Autowired
	private PartnerInvitationService	partnerInvitationService;
	@Autowired
	private AlumnService				alumnService;


	@Test
	public void acceptreject() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, 158, "ACCEPTED"
			}, {
				"alumn1", null, 158, "ACCEPTED"
			}, {
				"alumn2", null, 157, "REJECTED"
			}, {
				"admin", NullPointerException.class, 157, "ACCEPTED"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.acceptrejectTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3]);
	}

	protected void acceptrejectTemplate(final String username, final Class<?> expected, final int id, final String acceptreject) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final PartnerInvitation pi = this.partnerInvitationService.findOne(id);

			final Alumn alumn = this.alumnService.findByPrincipal();
			System.out.println(pi.getState());
			Assert.isTrue(pi.getState().equals("PENDING"));
			pi.setState(acceptreject);
			Assert.isTrue(alumn.getId() == (pi.getInvitationReceiver().getId()));
			this.partnerInvitationService.save(pi);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
