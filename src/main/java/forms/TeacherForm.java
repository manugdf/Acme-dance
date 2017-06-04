package forms;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import domain.DanceClass;

public class TeacherForm {
	
	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	picture;
	private String	presentationVideo;
	private boolean	acceptTerms;
	private String	username;
	private String	password;
	private String	confirmPassword;
	private String	newpassword;
	private String	repeatnewpassword;
//	private DanceClass danceClass;
	
	
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

	//@NotBlank
	@Pattern(regexp = "\\+\\d{1,3}?[ -]?\\d{6,14}|\\d{6,14}$")
	public String getPhone() {
		return this.phone;
	}
	public void setPhone(final String phone) {
		this.phone = phone;
	}
	
	
	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@URL
	public String getPresentationVideo() {
		return this.presentationVideo;
	}
	public void setPresentationVideo(final String presentationVideo) {
		this.presentationVideo = presentationVideo;
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

	@Size(min = 5, max = 32)
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
	
//	public DanceClass getDanceClass(){
//		return this.danceClass;
//	}
//	public void setDanceClass(final DanceClass danceClass){
//		this.danceClass=danceClass;
//	}

}
