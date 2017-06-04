
package usecases;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import services.ActorService;
import services.CensoredWordsService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU37 extends AbstractTest {

	//CU37 - Un usuario autenticado como administrador modifica la lisra de palabras censuradas.

	@Autowired
	private CensoredWordsService	censoredWordsService;
	@Autowired
	private ActorService			actorService;


	@Test
	public void addWord() {
		final Object[][] testingData = {
			{
				"hola", 96, null
			}, {
				"hola", 1000, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.addWordTemplate((String) testingData[i][0], (int) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void addWordTemplate(final String string, final int id, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Actor admin = this.actorService.findOne(97);
		try {
			this.authenticate(admin.getUserAccount().getUsername());
			this.censoredWordsService.add(string, id);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		System.out.println(caught);
		this.checkExceptions(expected, caught);
	}

}
