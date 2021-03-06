
package forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CompetitionPlannerForm {

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	username;
	private String	password;
	private String	repeatPassword;
	private String	repeatNewPassword;

	private String	newPassword;
	private String	companyName;
	private String	picture;
	private boolean	acceptTerms;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Size(min = 5, max = 32)
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

	@Size(min = 5, max = 32)
	public String getRepeatPassword() {
		return this.repeatPassword;
	}

	public void setRepeatPassword(final String password) {
		this.repeatPassword = password;
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

	@Pattern(regexp = "\\+\\d{1,3}?[ -]?\\d{6,14}|\\d{6,14}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}

	public boolean isAcceptTerms() {
		return this.acceptTerms;
	}
	public void setAcceptTerms(final boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}

	public String getNewPassword() {
		return this.newPassword;
	}
	public void setNewPassword(final String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRepeatNewPassword() {
		return this.repeatNewPassword;
	}
	public void setRepeatNewPassword(final String repeatNewPassword) {
		this.repeatNewPassword = repeatNewPassword;
	}

}
