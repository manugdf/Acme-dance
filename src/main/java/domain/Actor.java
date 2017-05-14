
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public abstract class Actor extends DomainEntity {

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;


	@NotBlank
	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	@Email
	@NotBlank
	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}


	//Relationships

	private Collection<Message>	messagesSended;
	private Collection<Message>	messagesReceived;


	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "sender")
	public Collection<Message> getMessagesSended() {
		return this.messagesSended;
	}
	public void setMessagesSended(final Collection<Message> messagesSended) {
		this.messagesSended = messagesSended;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getMessagesReceived() {
		return this.messagesReceived;
	}
	public void setMessagesReceived(final Collection<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

}
