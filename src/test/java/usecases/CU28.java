
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Alumn;
import domain.Event;
import services.AlumnService;
import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU28 extends AbstractTest {

	@Autowired
	private EventService	eventService;

	@Autowired
	private AlumnService	alumnService;


	//CU28 - Un usuario logueado como alumno accede a la vista de una escuela,
	//ve los eventos y decide apuntarse a uno de ellos, siempre que el alumno
	//esté apuntado a alguna clase de la escuela en cuestión y haya espacio
	//libre en el evento.

	@Test
	public void assistEvent() {
		final Object[][] testingData = {
			{
				null, IllegalArgumentException.class, 136
			}, {
				"alumn1", null, 136
			}, {
				"alumn1", IllegalArgumentException.class, 139
			}, {
				"alumn2", null, 136
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.assistEventTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void assistEventTemplate(final String username, final Class<?> expected, final int id) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Alumn alumn = this.alumnService.findByPrincipal();
			Event event = this.eventService.findOne(id);
			Assert.isTrue(!event.getAlumns().contains(alumn));
			event.getAlumns().add(alumn);
			this.eventService.save(event);
			event = this.eventService.findOne(id);
			Assert.isTrue(event.getAlumns().contains(alumn));

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
}
