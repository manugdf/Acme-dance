
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class CensoredWords extends DomainEntity {

	private String[]	words;


	@NotNull
	public String[] getWords() {
		return this.words;
	}

	public void setWords(final String[] words) {
		this.words = words;
	}

}
