
package usecases;

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
public class CU5 extends AbstractTest {

	//CU5 - Un usuario autenticado envia un mensaje a otro actor del sistema.

	@Autowired
	private MessageService	messageService;
	@Autowired
	private ActorService	actorService;


	@Test
	public void sendMessage() {
		final Object[][] testingData = {
			{
				98, 99, "Hola", null
			}, {
				98, 99, null, NullPointerException.class
			}
		};
		for (int i = 0; i < testingData.length; i++)
			this.sendMessageTemplate((int) testingData[i][0], (int) testingData[i][1], (String) testingData[i][2], (Class<?>) testingData[i][3]);
	}

	protected void sendMessageTemplate(final int ids, final int idr, final String string, final Class<?> expected) {
		Class<?> caught;
		caught = null;
		final Actor sender = this.actorService.findOne(ids);
		final Actor receiver = this.actorService.findOne(idr);
		try {
			this.authenticate(sender.getUserAccount().getUsername());
			final Message message = this.messageService.create();
			message.setSender(sender);
			message.setReceiver(receiver);
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
