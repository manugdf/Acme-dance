
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import domain.Material;
import domain.Teacher;
import services.MaterialService;
import services.TeacherService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU24 extends AbstractTest {

	//CU24 - Un usuario autenticado como profesor desea eliminar un material.

	@Autowired
	private MaterialService	materialService;
	@Autowired
	private TeacherService	teacherService;


	@Test
	public void deleteMaterial() {
		final Object[][] testingData = {
			{
				149, null
			}, {
				148, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteMaterialTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void deleteMaterialTemplate(final int id, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Teacher teacher = this.teacherService.findOne(107);
		final Material material = this.materialService.findOne(id);
		try {
			this.authenticate(teacher.getUserAccount().getUsername());
			Assert.isTrue(material.getTeacher().getId() == 107);
			this.materialService.delete(material);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		System.out.println(caught);
		this.checkExceptions(expected, caught);
	}

}
