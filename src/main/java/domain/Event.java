
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Event extends DomainEntity {

	private String	title;
	private String	description;
	private Date	startDate;
	private double	duration;
	private int		maxAlumns;


	@NotBlank
	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@Min(0)
	public double getDuration() {
		return this.duration;
	}
	public void setDuration(final double duration) {
		this.duration = duration;
	}

	@Min(1)
	public int getMaxAlumns() {
		return this.maxAlumns;
	}
	public void setMaxAlumns(final int maxAlumns) {
		this.maxAlumns = maxAlumns;
	}


	//Relationship

	private Collection<Alumn>	alumns;
	private DanceSchool			danceSchool;


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
	public DanceSchool getDanceSchool() {
		return this.danceSchool;
	}
	public void setDanceSchool(final DanceSchool danceSchool) {
		this.danceSchool = danceSchool;
	}

}
