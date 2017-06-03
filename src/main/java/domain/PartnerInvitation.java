
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

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "state")
})
public class PartnerInvitation extends DomainEntity {

	private String	state;
	private String	comment;
	private String	danceStyle;


	@NotBlank
	@Pattern(regexp = "^PENDING$|^ACCEPTED$|^REJECTED$")
	public String getState() {
		return this.state;
	}
	public void setState(final String state) {
		this.state = state;
	}

	public String getComment() {
		return this.comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	}

	@NotBlank
	public String getDanceStyle() {
		return this.danceStyle;
	}
	public void setDanceStyle(final String danceStyle) {
		this.danceStyle = danceStyle;
	}


	//Relationship

	private Alumn	invitationSender;
	private Alumn	invitationReceiver;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Alumn getInvitationSender() {
		return this.invitationSender;
	}
	public void setInvitationSender(final Alumn invitationSender) {
		this.invitationSender = invitationSender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Alumn getInvitationReceiver() {
		return this.invitationReceiver;
	}
	public void setInvitationReceiver(final Alumn invitationReceiver) {
		this.invitationReceiver = invitationReceiver;
	}

}
