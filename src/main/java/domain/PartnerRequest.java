
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class PartnerRequest extends DomainEntity {

	private String	description;
	private String	danceStyle;


	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotBlank
	public String getDanceStyle() {
		return this.danceStyle;
	}
	public void setDanceStyle(final String danceStyle) {
		this.danceStyle = danceStyle;
	}


	//Relationship

	private Alumn	alumn;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Alumn getAlumn() {
		return this.alumn;
	}
	public void setAlumn(final Alumn alumn) {
		this.alumn = alumn;
	}

}
