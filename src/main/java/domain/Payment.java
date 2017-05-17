
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Payment {

	private String	paymentType;
	private Date	startDate;
	private Date	endDate;


	@NotBlank
	@Pattern(regexp = "^MONTHLY$|^YEARLY$")
	public String getPaymentType() {
		return this.paymentType;
	}
	public void setPaymentType(final String paymentType) {
		this.paymentType = paymentType;
	}

	@NotNull
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	//relationships

	private DanceClass	danceClass;
	private Alumn		alumn;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public DanceClass getDanceClass() {
		return this.danceClass;
	}

	public void setDanceClass(final DanceClass danceClass) {
		this.danceClass = danceClass;
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
