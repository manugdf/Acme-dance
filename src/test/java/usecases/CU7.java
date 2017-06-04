
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
public class CU7 extends AbstractTest {

	//CU7 - Un usuario autenticado responde un mensaje que le ha enviado otro actor del sistema.

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void replyMessage() {
		final Object[][] testingData = {
			{
				107, "Hola", null
			}, {
				98, null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.replyMessageTemplate((int) testingData[i][0], (String) testingData[i][1], (Class<?>) testingData[i][2]);
	}

	protected void replyMessageTemplate(final int ids, final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Actor sender = this.actorService.findOne(ids);

		try {
			this.authenticate(sender.getUserAccount().getUsername());
			final ArrayList<Message> messages = new ArrayList<>(sender.getMessagesReceived());
			final Message recivied = messages.get(0);
			final Message message = this.messageService.create();
			message.setReceiver(recivied.getSender());
			message.setSender(sender);
			message.setCopy(true);
			message.setSubject("Hola");
			message.setBody(string);
			this.messageService.sendMessage(message);
			this.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
