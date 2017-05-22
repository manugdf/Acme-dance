
package services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.CensoredWords;
import repositories.CensoredWordsRepository;

@Service
@Transactional
public class CensoredWordsService {

	//Repositories------------------------------------------------
	@Autowired
	private CensoredWordsRepository censoredWordsRepository;


	//CRUD Methods------------------------------------------------

	public CensoredWords create() {
		final CensoredWords censoredWords = new CensoredWords();
		censoredWords.setWords(new ArrayList<String>());

		return censoredWords;
	}

	public CensoredWords findCenWords() {
		return this.censoredWordsRepository.findOne(74);
	}

	public CensoredWords save(final String string) {
		Assert.notNull(string);
		final CensoredWords censoredWords = this.findCenWords();
		censoredWords.getWords().add(string);
		final CensoredWords words = this.censoredWordsRepository.save(censoredWords);

		return words;
	}

	public CensoredWords delete(final String string) {
		Assert.notNull(string);
		final CensoredWords censoredWords = this.findCenWords();
		censoredWords.getWords().remove(string);
		final CensoredWords words = this.censoredWordsRepository.save(censoredWords);

		return words;
	}

	//Other Methods-----------------------------------------------------------------

}
