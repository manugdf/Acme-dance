
package forms;

import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import domain.Actor;

public class MessageForm {

	private String	subject;
	private String	body;
	private Actor	receiver;


	@NotBlank
	public String getSubject() {
		return this.subject;
	}
	public void setSubject(final String subject) {
		this.subject = subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}
	public void setBody(final String body) {
		this.body = body;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getReceiver() {
		return this.receiver;
	}
	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

}
