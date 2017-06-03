
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Schedule extends DomainEntity {

	private String	dayOfWeek;
	private Date	startDate;
	private Date	endTime;
	private String	classroom;


	@NotBlank
	@Pattern(regexp = "^MONDAY$|^TUESDAY$|^WEDNESDAY$|^THURSDAY$|^FRIDAY$|^SATURDAY$|^SUNDAY$")
	public String getDayOfWeek() {
		return this.dayOfWeek;
	}
	public void setDayOfWeek(final String dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}

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
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getEndTime() {
		return this.endTime;
	}
	public void setEndTime(final Date endTime) {
		this.endTime = endTime;
	}

	public String getClassroom() {
		return this.classroom;
	}
	public void setClassroom(final String classroom) {
		this.classroom = classroom;
	}


	//Relationship

	private DanceClass	danceClass;


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
