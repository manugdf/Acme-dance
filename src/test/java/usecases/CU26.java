package usecases;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceCertificate;
import services.DanceCertificateService;

import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU26 extends AbstractTest{
	
	@Autowired
	private DanceCertificateService danceCertificateService;


	//CU26 - Un usuario registrado como profesor accede a la vista de clases que imparte, accede a la vista de exámenes, 
	// selecciona uno ya finalizado y accede a una vista donde selecciona uno de los alumnos para agregarle un certificado con el nivel alcanzado, 
	// además muestra la lista de certificados del alumno



	@Test
	public void createDanceTest() {
		final Object[][] testingData = {
			{
				"teacher1", null, 98, 145
			}
			,{
				"admin", IllegalArgumentException.class, 98, 145
			}, {
				null, IllegalArgumentException.class, 98, 145
			}, {
				"manager1", IllegalArgumentException.class, 98, 145
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createDanceTestTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3]);
	}

	protected void createDanceTestTemplate(final String username, final Class<?> expected, int alumnId, int danceTestId) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			DanceCertificate danceCertificate=new DanceCertificate();
			danceCertificate.setDanceLevel("BASIC");
			danceCertificate.setCertificateDate(new Date("2017/12/12 00:00"));
			
			danceCertificateService.createDanceCertificate(danceCertificate, alumnId, danceTestId);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
