
package usecases;

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
public class CU39 extends AbstractTest {
	//CU39 - Un usuario logueado como admin accede a una vista donde ve todos
	//los banners del sistema y decide modificar uno que no esté pendiente.

	@Autowired
	private BannerService bannerService;


	@Test
	public void purposeBanner() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "banner.jpg"
			}, {
				"manager1", null, "http://www.banner.com/banner1.jpg"
			}, {
				"manager2", null, "http://www.banner.com/banner2.jpg"
			}, {
				"planner1", NullPointerException.class, "6767689293"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.purposeBannerTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void purposeBannerTemplate(final String username, final Class<?> expected, final String url) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			Banner banner = this.bannerService.create();
			for (final Banner b : this.bannerService.findAll())
				if (b.getState() == "ACCEPTED")
					banner = b;
			banner.setUrl(url);
			this.bannerService.newBanner(banner);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
