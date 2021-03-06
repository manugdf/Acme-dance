
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Fee extends DomainEntity {

	//Atributes
	private double managerAmount;


	@Min(0)
	@Digits(integer = 9, fraction = 2)
	public double getManagerAmount() {
		return this.managerAmount;
	}

	public void setManagerAmount(final double managerAmount) {
		this.managerAmount = managerAmount;
	}
}
