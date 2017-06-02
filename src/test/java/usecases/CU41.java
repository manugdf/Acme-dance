package usecases;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Alumn;
import domain.Manager;
import domain.Teacher;
import services.AdminService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
		"classpath:spring/junit.xml"
	})
	@RunWith(SpringJUnit4ClassRunner.class)
	@Transactional
public class CU41 extends AbstractTest{
	
	@Autowired
	private AdminService adminService;

	//CU41 - Un usuario logueado como admin accede a una vista donde se muestra el dashboard con toda la información pertinente.


	@Test
	public void dahboardTest() {
		final Object[][] testingData = {
			{
				"admin", null
			}
			,{
				"manager1", IllegalArgumentException.class
			}, {
				null, IllegalArgumentException.class
			}, {
				"teacher1", IllegalArgumentException.class
			}
		};
		for (int i = 0; i < testingData.length; i++){
			this.template1((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template2((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template3((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template4((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template5((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template6((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template7((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template8((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template9((String) testingData[i][0], (Class<?>) testingData[i][1]);
			this.template10((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}
	}

	protected void template1(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Manager> managerMoreDanceSchoolAccepted = adminService.managerMoreDanceSchoolAccepted();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template2(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Integer acceptedDeniedRatio= adminService.acceptedDeniedRatio();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	protected void template3(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Teacher> bestRating=adminService.bestRating();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template4(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double messagesUsersRatio= adminService.messagesUsersRatio();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template5(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Actor> actorMoreMessageSend= adminService.actorMoreMessageSend();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template6(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Alumn> alumnsMoreClasses= adminService.alumnsMoreClasses();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}
	
	protected void template7(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Manager> managerMoreBannersAccepted = adminService.managerMoreBannersAccepted();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template8(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Collection<Teacher> teachersOrderedByRatio= adminService.teachersOrderedByRatio();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template9(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Object[] minAvgMaxClassesPerTeacher = adminService.minAvgMaxClassesPerTeacher();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	protected void template10(final String username, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		try {
			this.authenticate(username);
			final Double eventAverageDuration = adminService.eventAverageDuration();
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
