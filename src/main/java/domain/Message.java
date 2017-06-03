
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "copy")
})
public class Message extends DomainEntity {

	private Date	moment;
	private String	subject;
	private String	body;
	private boolean	copy;


	public boolean isCopy() {
		return this.copy;
	}

	public void setCopy(final boolean copy) {
		this.copy = copy;
	}

	@NotNull
	@Past
	public Date getMoment() {
		return this.moment;
	}
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

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


	//Relationships

	private Actor	sender;
	private Actor	receiver;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}
	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Actor getReceiver() {
		return this.receiver;
	}
	public void setReceiver(final Actor receiver) {
		this.receiver = receiver;
	}

}
