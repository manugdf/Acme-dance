
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CensoredWords;
import domain.Message;
import repositories.MessageRepository;

@Service
@Transactional
public class MessageService {

	//Repository------------------------------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	@Autowired
	private CensoredWordsService	censoredWordsService;


	//CRUD Methods----------------------------------------------------

	public Message save(final Message message) {
		Assert.notNull(message);
		final Message saved = this.messageRepository.save(message);
		return saved;
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
}
