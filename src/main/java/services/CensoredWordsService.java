
package services;

import java.util.ArrayList;
import java.util.Collection;

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
		final ArrayList<String> words = new ArrayList<>();
		words.add("sex");
		words.add("viagra");
		censoredWords.setWords(new ArrayList<String>());

		return censoredWords;
	}

	public CensoredWords findOne(final int id) {
		return this.censoredWordsRepository.findOne(id);
	}

	public Collection<CensoredWords> findAll() {
		return this.censoredWordsRepository.findAll();
	}

	public CensoredWords save(final CensoredWords censoredWords) {
		Assert.notNull(censoredWords);
		final CensoredWords res = this.censoredWordsRepository.save(censoredWords);
		return res;
	}

	//Other Methods-----------------------------------------------------------------
	public CensoredWords add(final String string, final int id) {
		Assert.notNull(string);
		final CensoredWords censoredWords = this.findOne(id);
		censoredWords.getWords().add(string);
		final CensoredWords words = this.save(censoredWords);
		return words;
	}

}
