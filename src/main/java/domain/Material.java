
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Material extends DomainEntity {

	private String	title;
	private String	description;
	private String	link;


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

	@NotBlank
	@URL
	public String getLink() {
		return this.link;
	}
	public void setLink(final String link) {
		this.link = link;
	}


	//Relationship

	private Teacher		teacher;
	private DanceClass	danceClass;


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
	public DanceClass getDanceClass() {
		return this.danceClass;
	}
	public void setDanceClass(final DanceClass danceClass) {
		this.danceClass = danceClass;
	}

}
