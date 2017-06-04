
package usecases;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.Message;
import services.ActorService;
import services.MessageService;
import utilities.AbstractTest;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU6 extends AbstractTest {

	//CU6 - Un usuario autenticado borra un mensaje.

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void deleteMessage() {
		final Object[][] testingData = {
			{
				98, null
			}, {
				99, IndexOutOfBoundsException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.deleteMessageTemplate((int) testingData[i][0], (Class<?>) testingData[i][1]);
	}

	protected void deleteMessageTemplate(final int ids, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Actor sender = this.actorService.findOne(ids);
		try {
			this.authenticate(sender.getUserAccount().getUsername());
			final ArrayList<Message> messages = new ArrayList<>(sender.getMessagesSended());
			final Message message = messages.get(0);
			this.messageService.deleteSent(message);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

}
