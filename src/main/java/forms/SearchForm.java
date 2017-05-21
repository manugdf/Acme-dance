
package forms;

import org.hibernate.validator.constraints.NotBlank;

public class SearchForm {

	private String word;


	public SearchForm() {
		super();
	}

	@NotBlank
	public String getWord() {
		return this.word;
	}

	public void setWord(final String word) {
		this.word = word;
	}

}
