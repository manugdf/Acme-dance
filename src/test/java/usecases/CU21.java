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
public class CU21 extends AbstractTest{

	@Autowired
	private TeacherService teacherService;


	//CU21 -Un usuario logueado como empresario accede a la vista de profesores y decide modificar los datos de uno


	@Test
	public void modifyTeacher() {
		final Object[][] testingData = {
			{
				"manager1", null, 107
			}
			,{
				"manager1", NullPointerException.class, 0
			}, {
				null, IllegalArgumentException.class, 107
			}, {
				"admin", IllegalArgumentException.class, 107
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.modifyTeacherTemplate((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
	}

	protected void modifyTeacherTemplate(final String username, final Class<?> expected, int teacherId) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			
			Teacher teacher=teacherService.findOne(teacherId);
			teacher.setName("Manolay");
			teacher.getUserAccount().setUsername("manolay");
			teacher.setSurname("Diaz");
			teacher.setEmail("manolay@gmail.com");
			
			Assert.isTrue(teacher.getEmail().contains("@"));
			teacher.setPhone("659183896");
			Assert.notNull(teacher.getPhone());
			
			teacher.setPicture("http://test.com/test");
			teacher.setPresentationVideo("http://test.com/testVideo");
			
			this.teacherService.modify(teacher);
			
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
