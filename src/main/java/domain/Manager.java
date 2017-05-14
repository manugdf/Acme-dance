
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Manager extends Actor {

	private CreditCard	creditCard;
	private double		fee;


	@NotNull
	@Valid
	public CreditCard getCreditCard() {
		return this.creditCard;
	}
	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public double getFee() {
		return this.fee;
	}
	public void setFee(final double fee) {
		this.fee = fee;
	}


	//RelationShip

	private Collection<DanceSchool>	danceSchools;
	private Collection<Banner>		banner;


	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<DanceSchool> getDanceSchools() {
		return this.danceSchools;
	}
	public void setDanceSchools(final Collection<DanceSchool> danceSchools) {
		this.danceSchools = danceSchools;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "manager")
	public Collection<Banner> getBanner() {
		return this.banner;
	}
	public void setBanner(final Collection<Banner> banner) {
		this.banner = banner;
	}

}
