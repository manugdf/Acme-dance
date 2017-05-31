
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import security.UserAccount;

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

	@Pattern(regexp = "\\+\\d{1,3}?[ -]?\\d{6,14}|\\d{6,14}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}


	//Relationships

	private Collection<Message>	messagesSended;
	private Collection<Message>	messagesReceived;
	private UserAccount			userAccount;


	@Valid
	@NotNull
	@OneToOne(optional = false, cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@OneToMany(mappedBy = "sender")
	public Collection<Message> getMessagesSended() {
		return this.messagesSended;
	}
	public void setMessagesSended(final Collection<Message> messagesSended) {
		this.messagesSended = messagesSended;
	}

	@OneToMany(mappedBy = "receiver")
	public Collection<Message> getMessagesReceived() {
		return this.messagesReceived;
	}
	public void setMessagesReceived(final Collection<Message> messagesReceived) {
		this.messagesReceived = messagesReceived;
	}

}
