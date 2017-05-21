
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class DanceSchool extends DomainEntity {

	private String		name;
	private String		description;
	private Location	location;
	private String		phone;
	private String		picture;
	private String		state;


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

	@NotNull
	@Valid
	public Location getLocation() {
		return this.location;
	}
	public void setLocation(final Location location) {
		this.location = location;
	}

	@NotBlank
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
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^REJECTED$")
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}


	//Relationship`

	private Manager					manager;
	private Collection<DanceClass>	danceClasses;
	private Collection<Event>		events;
	private Collection<Competition>	competitions;
	private Collection<Award>		awards;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}
	public void setManager(final Manager manager) {
		this.manager = manager;
	}

	@Valid
	@OneToMany(mappedBy = "danceSchool")
	public Collection<DanceClass> getDanceClasses() {
		return this.danceClasses;
	}
	public void setDanceClasses(final Collection<DanceClass> danceClasses) {
		this.danceClasses = danceClasses;
	}

	@NotNull
	@OneToMany(mappedBy = "danceSchool")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@NotNull
	@ManyToMany(mappedBy = "danceSchools")
	public Collection<Competition> getCompetitions() {
		return this.competitions;
	}
	public void setCompetitions(final Collection<Competition> competitions) {
		this.competitions = competitions;
	}

	@NotNull
	@OneToMany(mappedBy = "danceSchool")
	public Collection<Award> getAwards() {
		return this.awards;
	}
	public void setAwards(final Collection<Award> awards) {
		this.awards = awards;
	}

}
