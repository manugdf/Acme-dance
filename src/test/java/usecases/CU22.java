
package usecases;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.DanceClass;
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
public class CU22 extends AbstractTest {

	//CU22 - Un usuario autenticado como profesor desea crear un material.

	@Autowired
	private MaterialService	materialService;
	@Autowired
	private TeacherService	teacherService;


	@Test
	public void createMaterial() {
		final Object[][] testingData = {
			{
				107, "http://www.google.es", null
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.createMaterialTemplate((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void createMaterialTemplate(final int id, final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Teacher teacher = this.teacherService.findOne(id);
		final ArrayList<DanceClass> danceClasses = new ArrayList<>(teacher.getDanceClasses());
		try {
			this.authenticate(teacher.getUserAccount().getUsername());
			final Material material = this.materialService.create();
			material.setDanceClass(danceClasses.get(0));
			material.setTeacher(teacher);
			material.setDescription("hola");
			material.setLink(string);
			material.setTitle("hola");
			this.materialService.save(material);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
