
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Review extends DomainEntity {

	private int		score;
	private String	comment;


	@Range(min = 0, max = 5)
	public int getScore() {
		return this.score;
	}
	public void setScore(final int score) {
		this.score = score;
	}

	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
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
