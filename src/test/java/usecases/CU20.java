package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Teacher;
import services.TeacherService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU20 extends AbstractTest{
	
	@Autowired
	private TeacherService teacherService;


	//CU20 - Un usuario logueado como empresario accede a la vista de profesores decide registrar uno nuevo, 
	// además en la vista de sus clases que pertenecen a sus escuelas podrá asignar profesores o quitarlos


	@Test
	public void registerTeacher() {
		final Object[][] testingData = {
			{
				"manager1", null
			},
			{
				"admin", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}, {
				"teacher1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.registerTeacherTemplate((String) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void registerTeacherTemplate(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Teacher teacher=teacherService.create();
			teacher.setName("Manolay");
			teacher.getUserAccount().setUsername("manolay");
			teacher.setSurname("Diaz");
			teacher.setEmail("manolay@gmail.com");
			
			Assert.isTrue(teacher.getEmail().contains("@"));
			teacher.setPhone("659183896");
			Assert.notNull(teacher.getPhone());
			
			teacher.setPicture("http://test.com/test");
			teacher.setPresentationVideo("http://test.com/testVideo");
			
			this.teacherService.register(teacher);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
