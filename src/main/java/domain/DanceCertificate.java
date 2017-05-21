
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class DanceCertificate extends DomainEntity {

	private Date	certificateDate;
	private String	danceLevel;


	@NotNull
	public Date getCertificateDate() {
		return this.certificateDate;
	}
	public void setCertificateDate(final Date certificateDate) {
		this.certificateDate = certificateDate;
	}

	@NotNull
	@Pattern(regexp = "^BASIC$|^MEDIUM$|^HIGH$")
	public String getDanceLevel() {
		return this.danceLevel;
	}
	public void setDanceLevel(final String danceLevel) {
		this.danceLevel = danceLevel;
	}


	//Relationship

	private Teacher	teacher;
	private Alumn	alumn;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Teacher getTeacher() {
		return this.teacher;
	}
	public void setTeacher(final Teacher teacher) {
		this.teacher = teacher;
	}

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
