
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Award extends DomainEntity {

	private String	winnerName;
	private int		place;
	private String	prize;


	@NotBlank
	public String getWinnerName() {
		return this.winnerName;
	}
	public void setWinnerName(final String winnerName) {
		this.winnerName = winnerName;
	}

	@Min(1)
	public int getPlace() {
		return this.place;
	}
	public void setPlace(final int place) {
		this.place = place;
	}

	@NotBlank
	public String getPrize() {
		return this.prize;
	}
	public void setPrize(final String prize) {
		this.prize = prize;
	}


	//Relationship

	private Competition	competition;
	private DanceSchool	danceSchool;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Competition getCompetition() {
		return this.competition;
	}
	public void setCompetition(final Competition competition) {
		this.competition = competition;
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
