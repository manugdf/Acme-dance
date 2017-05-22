
package domain;

import java.util.ArrayList;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class CensoredWords extends DomainEntity {

	private ArrayList<String> words;


	@NotNull
	public ArrayList<String> getWords() {
		return this.words;
	}

	public void setWords(final ArrayList<String> words) {
		this.words = words;
	}

}
