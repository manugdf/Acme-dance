
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class CompetitionPlanner extends Actor {

	private String	companyName;
	private String	picture;


	@NotBlank
	public String getCompanyName() {
		return this.companyName;
	}
	public void setCompanyName(final String companyName) {
		this.companyName = companyName;
	}

	@URL
	public String getPicture() {
		return this.picture;
	}
	public void setPicture(final String picture) {
		this.picture = picture;
	}


	//Relationship

	private Collection<Competition>	competitions;


	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "competitionPlanner")
	public Collection<Competition> getCompetitions() {
		return this.competitions;
	}
	public void setCompetitions(final Collection<Competition> competitions) {
		this.competitions = competitions;
	}

}
