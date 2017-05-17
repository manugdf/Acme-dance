
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class DanceTest extends DomainEntity {

	private Date		testDate;
	private DanceLevel	danceLevel;
	private Date		limitInscription;


	@NotNull
	public Date getTestDate() {
		return this.testDate;
	}
	public void setTestDate(final Date testDate) {
		this.testDate = testDate;
	}

	@NotNull
	public DanceLevel getDanceLevel() {
		return this.danceLevel;
	}
	public void setDanceLevel(final DanceLevel danceLevel) {
		this.danceLevel = danceLevel;
	}

	@NotNull
	public Date getLimitInscription() {
		return this.limitInscription;
	}
	public void setLimitInscription(final Date limitInscription) {
		this.limitInscription = limitInscription;
	}


	//Relationship
	private Collection<Alumn>	alumns;
	private DanceClass			danceClass;


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

}
