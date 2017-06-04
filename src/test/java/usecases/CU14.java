
package usecases;

import java.text.SimpleDateFormat;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repositories.ScheduleRepository;
import services.ScheduleService;
import utilities.AbstractTest;
import domain.Schedule;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU14 extends AbstractTest {

	@Autowired
	private ScheduleService		scheduleService;

	@Autowired
	private ScheduleRepository	scheduleRepository;


	//CU14 -  Un usuario logueado como empresario decide 
	//modificar un horario de una de sus clases de su escuela. 

	@Test
	public void editSchedule() {
		final Object[][] testingData = {
			{
				"manager1", null, 127, "MONDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"manager1", null, 127, "MONDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"manager2", null, 128, "WEDNESDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				//Se intenta modificar un horario que no pertenece a una clase de su escuela
				"manager1", IllegalArgumentException.class, 128, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {

				"manager1", IllegalArgumentException.class, 127, "TUESDAY", "10/10/2018 20:00", "10/10/1993 21:00"
			}, {
				null, IllegalArgumentException.class, 127, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"planner1", IllegalArgumentException.class, 127, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"alumn1", IllegalArgumentException.class, 127, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"manager1", ConstraintViolationException.class, 127, "", "10/10/2018 20:00", "10/10/2018 21:00"
			}, {
				"manager1", ConstraintViolationException.class, 127, "asd", "10/10/2018 20:00", "10/10/2018 21:00"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.editScheduleTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5]);
	}
	protected void editScheduleTemplate(final String username, final Class<?> expected, final int scheduleClassID, final String dayOfWeek, final String startDate, final String endTime) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			final Schedule s = this.scheduleService.findOne(scheduleClassID);

			s.setDayOfWeek(dayOfWeek);
			s.setEndTime(formatter.parse(endTime));
			s.setStartDate(formatter.parse(startDate));

			this.scheduleService.createSchedule(s);

			this.scheduleRepository.flush();

			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
