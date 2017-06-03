
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Competition extends DomainEntity {

	private Date		startDate;
	private Location	place;
	private String		description;
	private int			alumnPerSchool;
	private int			limitSchool;
	private Date		limitInscription;


	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Valid
	public Location getPlace() {
		return this.place;
	}
	public void setPlace(final Location place) {
		this.place = place;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(1)
	public int getAlumnPerSchool() {
		return this.alumnPerSchool;
	}
	public void setAlumnPerSchool(final int alumnPerSchool) {
		this.alumnPerSchool = alumnPerSchool;
	}

	@Min(1)
	public int getLimitSchool() {
		return this.limitSchool;
	}
	public void setLimitSchool(final int limitSchool) {
		this.limitSchool = limitSchool;
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

	private CompetitionPlanner		competitionPlanner;
	private Collection<DanceSchool>	danceSchools;
	private Collection<Award>		awards;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public CompetitionPlanner getCompetitionPlanner() {
		return this.competitionPlanner;
	}
	public void setCompetitionPlanner(final CompetitionPlanner competitionPlanner) {
		this.competitionPlanner = competitionPlanner;
	}

	@NotNull
	@ManyToMany
	public Collection<DanceSchool> getDanceSchools() {
		return this.danceSchools;
	}
	public void setDanceSchools(final Collection<DanceSchool> danceSchools) {
		this.danceSchools = danceSchools;
	}

	@NotNull
	@OneToMany(mappedBy = "competition")
	public Collection<Award> getAwards() {
		return this.awards;
	}
	public void setAwards(final Collection<Award> awards) {
		this.awards = awards;
	}

}
