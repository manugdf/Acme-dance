
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "state")
})
public class Banner extends DomainEntity {

	private String	url;
	private String	state;


	@NotNull
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^REJECTED$")
	public String getState() {
		return this.state;
	}

	public void setState(final String state) {
		this.state = state;
	}

	@URL
	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}


	//Relationship

	private Manager	manager;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Manager getManager() {
		return this.manager;
	}

	public void setManager(final Manager manager) {
		this.manager = manager;
	}

}
