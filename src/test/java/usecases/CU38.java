
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Banner;
import services.BannerService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU38 extends AbstractTest {

	//CU38 - Un usuario logueado como admin accede a una vista donde ve
	//los banners de escuelas propuestos por los empresarios y decide aceptar
	//una propuesta o rechazarla.

	@Autowired
	private BannerService bannerService;


	@Test
	public void aceptrejectSchool() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "ACCEPTED"
			}, {
				"admin", null, "ACCEPTED"
			}, {
				"admin", null, "REJECTED"
			}, {
				"planner1", IllegalArgumentException.class, "ACCEPTED"
			}
		};

		for (int i = 0; i < testingData.length; i++)
			this.acceptrejectTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void acceptrejectTemplate(final String username, final Class<?> expected, final String dato) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Banner> banner = this.bannerService.findAllPending();
			Banner res = new Banner();
			for (final Banner b : banner)
				res = b;
			if (dato == "ACCEPTED")
				this.bannerService.acceptBanner(res.getId());
			if (dato == "REJECTED")
				this.bannerService.rejectBanner(res.getId());

			this.unauthenticate();

			res.setState("PENDING");
			this.bannerService.save(res);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
