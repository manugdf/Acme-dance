
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.CensoredWords;
import domain.Message;
import forms.MessageForm;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	//Repository------------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private CensoredWordsService	censoredWordsService;

	//Services--------------------------------------------------------
	@Autowired
	private ActorService			actorService;


	//CRUD Methods----------------------------------------------------

	public Message create() {
		final Message message = new Message();
		message.setMoment(new Date(System.currentTimeMillis() - 1000));
		message.setSender(this.actorService.findByPrincipal());
		return message;
	}

	public Message findOne(final int id) {
		Message message;
		message = this.messageRepository.findOne(id);
		return message;
	}

	public Collection<Message> findAllReceived() {
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Message> received = actor.getMessagesReceived();
		return received;
	}

	public Collection<Message> findAllSent() {
		final Actor actor = this.actorService.findByPrincipal();
		final Collection<Message> sent = actor.getMessagesSended();
		return sent;
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final Message saved = this.messageRepository.save(message);
		return saved;
	}

	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	//Other Method----------------------------------------------------

	public Message spam(final Message message) {
		Assert.notNull(message);
		final String[] subject = message.getSubject().split(" ");
		final String[] body = message.getBody().split(" ");
		final String aux = "****";
		final CensoredWords censoredWords = new ArrayList<>(this.censoredWordsService.findAll()).get(0);
		final ArrayList<String> subAL = new ArrayList<>();
		final ArrayList<String> bodAL = new ArrayList<>();

		for (String string : subject) {
			if (censoredWords.getWords().contains(string)) {
				string = aux;
				subAL.add(string);
			}
			subAL.add(string);
		}

		String newSub = new String();
		for (int i = 0; i < subAL.size(); i++)
			newSub += subAL.get(i) + " ";

		for (String string : body) {
			if (censoredWords.getWords().contains(string)) {
				string = aux;
				bodAL.add(string);
			}
			bodAL.add(string);
		}

		String newBod = new String();
		for (int i = 0; i < bodAL.size(); i++)
			newBod += bodAL.get(i) + " ";

		message.setBody(newBod);
		message.setSubject(newSub);

		final Message saved = this.save(message);

		return saved;

	}

	public Message reconstruct(final MessageForm messageForm) {
		final Message message = this.create();
		message.setBody(messageForm.getBody());
		message.setSubject(messageForm.getSubject());
		message.setReceiver(messageForm.getReceiver());
		return message;
	}
}
