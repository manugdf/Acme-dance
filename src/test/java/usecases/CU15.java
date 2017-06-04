
package usecases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.EventRepository;
import services.DanceSchoolService;
import services.EventService;
import utilities.AbstractTest;
import domain.Event;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU15 extends AbstractTest {

	@Autowired
	private EventService		eventService;

	@Autowired
	private EventRepository		eventRepository;

	@Autowired
	private DanceSchoolService	danceSchoolService;


	//CU15 -  Un usuario logueado como empresario decide 
	//crear un nuevo evento para una de sus escuelas. 

	@Test
	public void createEvent() {
		final Object[][] testingData = {
			{
				"manager1", null, 114, "title", "description", "10/10/2018 21:00", 1.0, 1
			}, {
				"manager2", null, 115, "title", "description", "10/10/2018 21:00", 1.0, 1
			}, {
				"manager2", IllegalArgumentException.class, 114, "title", "description", "10/10/2018 21:00", 1.0, 1
			}, {
				"manager2", ConstraintViolationException.class, 115, "", "description", "10/10/2018 21:00", 1.0, 1
			}, {
				"manager2", ConstraintViolationException.class, 115, "title", "", "10/10/2018 21:00", 1.0, 1
			}, {
				"manager2", ConstraintViolationException.class, 115, "title", "description", "10/10/2018 21:00", -1.0, 1
			}, {
				"manager2", ConstraintViolationException.class, 115, "title", "description", "10/10/2018 21:00", 1.0, 0
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createEventTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (double) testingData[i][6], (int) testingData[i][7]);
	}
	protected void createEventTemplate(final String username, final Class<?> expected, final int danceSchoolID, final String title, final String description, final String startDate, final double duration, final int maxAlumns) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			final Event e = this.eventService.create();

			e.setDanceSchool(this.danceSchoolService.findOne(danceSchoolID));
			e.setTitle(title);
			e.setDescription(description);
			e.setDuration(duration);
			e.setStartDate(formatter.parse(startDate));
			e.setMaxAlumns(maxAlumns);

			this.eventService.newOrEditEvent(e);
			this.eventRepository.flush();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
