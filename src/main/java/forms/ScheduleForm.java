
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import domain.DanceClass;

public class ScheduleForm {

	private int			scheduleId;
	private String		dayOfWeek;
	private Date		startDate;
	private Date		endTime;
	private String		classroom;
	private DanceClass	danceClass;


	@NotNull
	@Valid
	public DanceClass getDanceClass() {
		return this.danceClass;
	}
	public void setDanceClass(final DanceClass danceClass) {
		this.danceClass = danceClass;
	}

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
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Future
	public Date getStartDate() {
		return this.startDate;
	}
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
	@Future
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

	public int getScheduleId() {
		return this.scheduleId;
	}
	public void setScheduleId(final int scheduleId) {
		this.scheduleId = scheduleId;
	}

}
