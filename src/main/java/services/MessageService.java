
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
		message.setCopy(true);
		return message;
	}

	public Message findOne(final int id) {
		Message message;
		message = this.messageRepository.findOne(id);
		return message;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message save(final Message message) {
		Assert.notNull(message);
		final Message censurado = this.spam(message);
		return this.messageRepository.save(censurado);
	}
	public void delete(final Message message) {
		this.messageRepository.delete(message);
	}

	//Other Method----------------------------------------------------

	public Message spam(final Message message) {
		Assert.notNull(message);
		final String[] subject = message.getSubject().toLowerCase().split(" ");
		final String[] body = message.getBody().toLowerCase().split(" ");
		final String aux = "****";
		final CensoredWords censoredWords = new ArrayList<>(this.censoredWordsService.findAll()).get(0);
		final ArrayList<String> subAL = new ArrayList<>();
		final ArrayList<String> bodAL = new ArrayList<>();

		for (int i = 0; i < subject.length; i++)
			if (censoredWords.getWords().contains(subject[i]))
				subAL.add(aux);
			else
				subAL.add(subject[i]);

		String newSub = new String();
		for (int i = 0; i < subAL.size(); i++)
			newSub += subAL.get(i) + " ";

		for (int i = 0; i < body.length; i++)
			if (censoredWords.getWords().contains(body[i]))
				bodAL.add(aux);
			else
				bodAL.add(body[i]);

		String newBod = new String();
		for (int i = 0; i < bodAL.size(); i++)
			newBod += bodAL.get(i) + " ";

		message.setBody(newBod);
		message.setSubject(newSub);

		return message;

	}

	public Message reconstruct(final MessageForm messageForm) {
		final Message message = this.create();
		message.setBody(messageForm.getBody());
		message.setSubject(messageForm.getSubject());
		message.setReceiver(messageForm.getReceiver());
		return message;
	}

	public void deleteReceived(final Message message) {
		final Actor principal = this.actorService.findByPrincipal();
		Assert.notNull(message);
		Assert.isTrue(principal.getId() == message.getReceiver().getId());
		this.messageRepository.delete(Integer.valueOf(message.getId()));
	}

	public void deleteSent(final Message message) {
		Assert.notNull(message);
		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal.getId() == message.getSender().getId());
		this.messageRepository.delete(Integer.valueOf(message.getId()));
	}

	public void sendMessage(final Message message) {

		final Actor receiverActor = message.getReceiver();
		final Message message2 = message;
		message2.setCopy(false);
		receiverActor.getMessagesReceived().add(message2);
		this.save(message2);
		this.actorService.save(receiverActor);

		final Message message3 = message;
		final Actor senderActor = this.actorService.findByPrincipal();
		message3.setCopy(true);
		senderActor.getMessagesSended().add(message3);
		this.save(message3);
		this.actorService.save(senderActor);

	}

	public Collection<Message> mySendedMessages(final int actorId) {
		final Collection<Message> sm = this.messageRepository.mySendedMessages(actorId);
		return sm;
	}

	public Collection<Message> myRecivedMessages(final int actorId) {
		final Collection<Message> sm = this.messageRepository.myRecivedMessages(actorId);
		return sm;
	}

}
