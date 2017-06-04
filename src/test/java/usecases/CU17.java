
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import services.EventService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU17 extends AbstractTest {

	@Autowired
	private EventService	eventService;


	//CU17 -  Un usuario logueado como empresario decide 
	//eliminar un evento para una de sus escuelas (siempre que no tenga alumnos apuntados). 

	@Test
	public void deleteEvent() {
		final Object[][] testingData = {
			{
				"manager1", IllegalArgumentException.class, 141
			}, {
				"manager2", IllegalArgumentException.class, 142
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createEventTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}
	protected void createEventTemplate(final String username, final Class<?> expected, final int eventID) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			this.eventService.delete(this.eventService.findOne(eventID));
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
