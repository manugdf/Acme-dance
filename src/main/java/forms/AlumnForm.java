
package forms;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import domain.CreditCard;

public class AlumnForm {

	private String		name;
	private String		surname;
	private String		email;
	private String		phone;
	private boolean		acceptTerms;
	private String		username;
	private String		password;
	private String		confirmPassword;
	private String		newpassword;
	private String		repeatnewpassword;
	private CreditCard	creditCard;


	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}
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

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(final String username) {
		this.username = username;
	}

	@Size(min = 5, max = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return this.confirmPassword;
	}

	public void setConfirmPassword(final String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public boolean isAcceptTerms() {
		return this.acceptTerms;
	}
	public void setAcceptTerms(final boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public String getNewpassword() {
		return this.newpassword;
	}
	public void setNewpassword(final String newpassword) {
		this.newpassword = newpassword;
	}

	public String getRepeatnewpassword() {
		return this.repeatnewpassword;
	}
	public void setRepeatnewpassword(final String repeatnewpassword) {
		this.repeatnewpassword = repeatnewpassword;
	}

}
