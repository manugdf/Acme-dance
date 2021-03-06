
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
	private double	yearlyPrice;
	private String	description;


	@Min(1)
	public int getMaxAlumns() {
		return this.maxAlumns;
	}
	public void setMaxAlumns(final int maxAlumns) {
		this.maxAlumns = maxAlumns;
	}

	@NotBlank
	public String getStyle() {
		return this.style;
	}
	public void setStyle(final String style) {
		this.style = style;
	}

	@Min(0)
	public double getMonthlyPrice() {
		return this.monthlyPrice;
	}
	public void setMonthlyPrice(final double monthlyPrice) {
		this.monthlyPrice = monthlyPrice;
	}

	@Min(0)
	public double getYearlyPrice() {
		return this.yearlyPrice;
	}
	public void setYearlyPrice(final double yearlyPrice) {
		this.yearlyPrice = yearlyPrice;
	}

	public String getDescription() {
		return this.description;
	}
	public void setDescription(final String description) {
		this.description = description;
	}


	//Relationship`

	private DanceSchool				danceSchool;
	private Collection<Schedule>	schedules;
	private Collection<Teacher>		teachers;
	private Collection<Material>	materials;
	private Collection<DanceTest>	danceTests;
	private Collection<Payment>		payments;


	@NotNull
	@Valid
	@OneToMany(mappedBy = "danceClass")
	public Collection<Payment> getPayments() {
		return this.payments;
	}
	public void setPayments(final Collection<Payment> payments) {
		this.payments = payments;
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
	@OneToMany(mappedBy = "danceClass")
	public Collection<DanceTest> getDanceTests() {
		return this.danceTests;
	}
	public void setDanceTests(final Collection<DanceTest> danceTests) {
		this.danceTests = danceTests;
	}

	@Override
	public String toString() {
		final String message = "" + this.description;
		return message;
	}

}
