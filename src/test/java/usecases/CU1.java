
package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.DanceSchool;
import services.DanceSchoolService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU1 extends AbstractTest {

	@Autowired
	private DanceSchoolService danceSchoolService;


	// CU 1 - Un usuario no logueado ve en la página principal los banners
	//publicitarios y accede a un buscador de escuelas de baile según su
	//localización o una palabra clave que deba aparecer en el nombre o
	//descripción de la escuela. Ve la lista resultante de escuelas, con los datos básicos de cada una
	//(nombre, descripción, localización, teléfono e imagen), sus profesores (con sus datos),
	//sus clases, los horarios de cada clase y el palmarés de premios de la escuela.

	@Test
	public void searchSchool() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, "tortilla"
			}, {
				"admin", null, "baila"
			}, {
				"alumn1", null, "utrera"
			}, {
				"planner1", IllegalArgumentException.class, "sarmiento"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.searchSchool((String) testingData[i][0], (Class<?>) testingData[i][1], (String) testingData[i][2]);
	}

	protected void searchSchool(final String username, final Class<?> expected, final String word) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<DanceSchool> allAccepted = this.danceSchoolService.findAllAccepted();
			Assert.notNull(allAccepted);

			final Collection<DanceSchool> byKeyword = this.danceSchoolService.findSchoolsByKeyword(word);
			Assert.notEmpty(byKeyword);
			for (final DanceSchool d : byKeyword)
				Assert.isTrue(d.getDescription().toLowerCase().contains(word.toLowerCase()) || d.getName().toLowerCase().contains(word.toLowerCase()) || d.getLocation().getAddress().toLowerCase().contains(word.toLowerCase())
					|| d.getLocation().getCity().toLowerCase().contains(word.toLowerCase()) || d.getLocation().getProvince().toLowerCase().contains(word.toLowerCase()));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
