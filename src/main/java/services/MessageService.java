
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
import security.LoginService;

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

		return this.messageRepository.save(message);
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

	public void deleteMessage(Message message) {
		if (message.getReceiver() != null && message.getReceiver().getUserAccount().getId() == LoginService.getPrincipal().getId()) {
			message.setReceiver(null);
			message = this.save(message);
		}
		if (message.getSender() != null && message.getSender().getUserAccount().getId() == LoginService.getPrincipal().getId()) {
			message.setSender(null);
			message = this.save(message);
		}
		if (message.getReceiver() == null)
			if (message.getSender() == null)
				this.delete(message);

	}

	//	public void deleteMessage(final int id) {
	//		Message message = this.findOne(id);
	//		if (message.getReceiver() != null && message.getReceiver().getId() == this.actorService.findByPrincipal().getId()) {
	//			final Actor receiver = message.getReceiver();
	//			final Collection<Message> received = receiver.getMessagesReceived();
	//			received.remove(message);
	//			message.setReceiver(null);
	//
	//		} else if (message.getSender() != null && message.getSender().getId() == this.actorService.findByPrincipal().getId()) {
	//			final Actor sender = message.getSender();
	//			final Collection<Message> sent = sender.getMessagesReceived();
	//			sent.remove(message);
	//			message.setSender(null);
	//		}
	//
	//		if (message.getReceiver() == null && message.getSender() == null)
	//			this.delete(message);
	//
	//	}

	public void sendMessage(final Message message) {

		final Actor receiverActor = message.getReceiver();

		receiverActor.getMessagesReceived().add(message);
		this.actorService.save(receiverActor);

		final Actor senderActor = this.actorService.findByPrincipal();
		senderActor.getMessagesSended().add(message);
		this.actorService.save(senderActor);

		this.save(message);

	}

}
