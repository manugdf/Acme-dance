
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
public class CU18 extends AbstractTest {

	//CU 18 - Un usuario logueado como empresario accede a una vista en la
	//cual puede ver la cuota actual por publicar un banner y sus
	//Banners enviados, y decide enviar al administrador una
	//propuesta de banner publicitario de una de sus escuelas para que
	//sea colocado en la vista principal, sólo tras comprobar el sistema
	//que la tarjeta de crédito del empresario es válida.

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
			final Banner banner = this.bannerService.create();
			banner.setUrl(url);
			this.bannerService.newBanner(banner);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
