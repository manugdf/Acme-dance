
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "limitInscription")
})
public class DanceTest extends DomainEntity {

	private Date	testDate;
	private String	danceLevel;
	private Date	limitInscription;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getTestDate() {
		return this.testDate;
	}
	public void setTestDate(final Date testDate) {
		this.testDate = testDate;
	}

	@NotBlank
	@Pattern(regexp = "^BASIC$|^MEDIUM$|^HIGH$")
	public String getDanceLevel() {
		return this.danceLevel;
	}
	public void setDanceLevel(final String danceLevel) {
		this.danceLevel = danceLevel;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getLimitInscription() {
		return this.limitInscription;
	}
	public void setLimitInscription(final Date limitInscription) {
		this.limitInscription = limitInscription;
	}


	//Relationship
	private Collection<Alumn>				alumns;
	private DanceClass						danceClass;
	private Collection<DanceCertificate>	danceCertificates;


	@NotNull
	@ManyToMany
	public Collection<Alumn> getAlumns() {
		return this.alumns;
	}
	public void setAlumns(final Collection<Alumn> alumns) {
		this.alumns = alumns;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public DanceClass getDanceClass() {
		return this.danceClass;
	}
	public void setDanceClass(final DanceClass danceClass) {
		this.danceClass = danceClass;
	}

	@NotNull
	@OneToMany
	public Collection<DanceCertificate> getDanceCertificates() {
		return this.danceCertificates;
	}
	public void setDanceCertificates(final Collection<DanceCertificate> danceCertificates) {
		this.danceCertificates = danceCertificates;
	}

}
