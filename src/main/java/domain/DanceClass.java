
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class DanceClass extends DomainEntity {

	private String	style;
	private int		maxAlumns;
	private double	monthlyPrice;
	private String	description;
	private int		minAttendance;


	@NotBlank
	public String getStyle() {
		return this.style;
	}
	public void setStyle(final String style) {
		this.style = style;
	}

	@Min(1)
	public int getMaxAlumns() {
		return this.maxAlumns;
	}
	public void setMaxAlumns(final int maxAlumns) {
		this.maxAlumns = maxAlumns;
	}

	@Min(0)
	public double getMonthlyPrice() {
		return this.monthlyPrice;
	}
	public void setMonthlyPrice(final double monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}

	@Min(1)
	public int getMinAttendance() {
		return this.minAttendance;
	}
	public void setMinAttendance(final int minAttendance) {
		this.minAttendance = minAttendance;
	}


	//Relationship`

	private DanceSchool				danceSchool;
	private Collection<Schedule>	schedules;
	private Collection<Teacher>		teachers;
	private Collection<Material>	materials;
	private Collection<Alumn>		alumns;
	private Collection<DanceTest>	danceTests;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public DanceSchool getDanceSchool() {
		return this.danceSchool;
	}
	public void setDanceSchool(final DanceSchool danceSchool) {
		this.danceSchool = danceSchool;
	}

	@NotNull
	@OneToMany(mappedBy = "danceClass")
	public Collection<Schedule> getSchedules() {
		return this.schedules;
	}
	public void setSchedules(final Collection<Schedule> schedules) {
		this.schedules = schedules;
	}

	@NotNull
	@ManyToMany
	public Collection<Teacher> getTeachers() {
		return this.teachers;
	}
	public void setTeachers(final Collection<Teacher> teachers) {
		this.teachers = teachers;
	}

	@NotNull
	@OneToMany(mappedBy = "danceClass")
	public Collection<Material> getMaterials() {
		return this.materials;
	}
	public void setMaterials(final Collection<Material> materials) {
		this.materials = materials;
	}

	@NotNull
	@ManyToMany
	public Collection<Alumn> getAlumns() {
		return this.alumns;
	}
	public void setAlumns(final Collection<Alumn> alumns) {
		this.alumns = alumns;
	}

	@NotNull
	@OneToMany(mappedBy = "danceClass")
	public Collection<DanceTest> getDanceTests() {
		return this.danceTests;
	}
	public void setDanceTests(final Collection<DanceTest> danceTests) {
		this.danceTests = danceTests;
	}

}
