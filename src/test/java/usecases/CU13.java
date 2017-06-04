
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
import services.DanceClassService;
import services.ScheduleService;
import utilities.AbstractTest;
import domain.Schedule;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU13 extends AbstractTest {

	@Autowired
	private ScheduleService		scheduleService;

	@Autowired
	private ScheduleRepository	scheduleRepository;

	@Autowired
	private DanceClassService	danceClassService;


	//CU13 -  Un usuario logueado como empresario decide 
	//crear un nuevo horario para una de las clases de su escuela. 

	@Test
	public void createSchedule() {
		final Object[][] testingData = {
			{
				"manager1", null, 118, "MONDAY", "10/10/2018 20:00", "10/10/2018 21:00", ""
			}, {
				"manager1", null, 121, "MONDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				"manager2", null, 119, "WEDNESDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				//Se intenta crear un horario para una clase que no petenece a su escuela
				"manager1", IllegalArgumentException.class, 119, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				//Se intenta crear un horario con la hora de comienzo más tarde que la hora de fin
				"manager1", IllegalArgumentException.class, 119, "TUESDAY", "10/10/2019 20:00", "10/10/2018 21:00", "classroom"
			}, {
				null, IllegalArgumentException.class, 118, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				"planner1", IllegalArgumentException.class, 118, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				"alumn1", IllegalArgumentException.class, 118, "TUESDAY", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				"manager1", ConstraintViolationException.class, 118, "", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}, {
				"manager1", ConstraintViolationException.class, 118, "asd", "10/10/2018 20:00", "10/10/2018 21:00", "classroom"
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createScheduleTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (String) testingData[i][3], (String) testingData[i][4], (String) testingData[i][5], (String) testingData[i][6]);
	}
	protected void createScheduleTemplate(final String username, final Class<?> expected, final int danceClassID, final String dayOfWeek, final String startDate, final String endTime, final String classroom) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);

			final SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");

			final Schedule s = this.scheduleService.create();

			s.setDanceClass(this.danceClassService.findOne(danceClassID));
			s.setClassroom(classroom);
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
