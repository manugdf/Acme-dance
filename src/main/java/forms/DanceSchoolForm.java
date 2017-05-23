
package forms;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class DanceSchoolForm {

	private String	name;
	private String	description;
	private String	phone;
	private String	picture;
	private String	address;
	private String	province;
	private String	city;
	private int		danceSchoolId;


	public int getDanceSchoolId() {
		return this.danceSchoolId;
	}

	public void setDanceSchoolId(final int danceSchoolId) {
		this.danceSchoolId = danceSchoolId;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

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

	@NotBlank
	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	@NotBlank
	public String getProvince() {
		return this.province;
	}

	public void setProvince(final String province) {
		this.province = province;
	}

	@NotBlank
	public String getCity() {
		return this.city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

}
