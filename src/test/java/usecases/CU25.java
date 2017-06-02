package usecases;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceTest;
import services.DanceTestService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU25 extends AbstractTest{
	
	@Autowired
	private DanceTestService danceTestService;


	//CU25 - Un usuario registrado como profesor accede a la vista de clases que imparte, 
	// accede a la vista de exámenes y decide crear un examen para una clase que está impartiendo.


	@Test
	public void createDanceTest() {
		final Object[][] testingData = {
			{
				"teacher1", null, 118
			}
			,{
				"admin", IllegalArgumentException.class, 118
			}, {
				null, IllegalArgumentException.class, 118
			}, {
				"manager1", IllegalArgumentException.class, 118
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createDanceTestTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void createDanceTestTemplate(final String username, final Class<?> expected, int danceClassId) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			DanceTest danceTest=danceTestService.create();
			danceTest.setDanceLevel("BASIC");
			danceTest.setLimitInscription(new Date("2017/11/12 00:00"));
			danceTest.setTestDate(new Date("2017/12/12 00:00"));
			
			danceTestService.createDanceTest(danceTest, danceClassId);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
