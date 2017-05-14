
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Alumn extends Actor {

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


	//Relationship

	private Collection<PartnerInvitation>	partnerInvitationSends;
	private Collection<PartnerInvitation>	partnerInvitationReceives;
	private Collection<PartnerRequest>		partnerRequests;
	private Collection<Review>				reviews;
	private Collection<DanceCertificate>	danceCertificates;
	private Collection<Event>				events;
	private Collection<DanceTest>			danceTests;
	private Collection<DanceClass>			danceClasses;


	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "invitationSender")
	public Collection<PartnerInvitation> getPartnerInvitationSends() {
		return this.partnerInvitationSends;
	}
	public void setPartnerInvitationSends(final Collection<PartnerInvitation> partnerInvitationSends) {
		this.partnerInvitationSends = partnerInvitationSends;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "invitationReceiver")
	public Collection<PartnerInvitation> getPartnerInvitationReceives() {
		return this.partnerInvitationReceives;
	}
	public void setPartnerInvitationReceives(final Collection<PartnerInvitation> partnerInvitationReceives) {
		this.partnerInvitationReceives = partnerInvitationReceives;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "alumn")
	public Collection<PartnerRequest> getPartnerRequests() {
		return this.partnerRequests;
	}
	public void setPartnerRequests(final Collection<PartnerRequest> partnerRequests) {
		this.partnerRequests = partnerRequests;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "alumn")
	public Collection<Review> getReviews() {
		return this.reviews;
	}
	public void setReviews(final Collection<Review> reviews) {
		this.reviews = reviews;
	}

	@ElementCollection
	@NotNull
	@OneToMany(mappedBy = "alumn")
	public Collection<DanceCertificate> getDanceCertificates() {
		return this.danceCertificates;
	}
	public void setDanceCertificates(final Collection<DanceCertificate> danceCertificates) {
		this.danceCertificates = danceCertificates;
	}

	@ElementCollection
	@NotNull
	@ManyToMany(mappedBy = "alumns")
	public Collection<Event> getEvents() {
		return this.events;
	}
	public void setEvents(final Collection<Event> events) {
		this.events = events;
	}

	@ElementCollection
	@NotNull
	@ManyToMany(mappedBy = "alumns")
	public Collection<DanceTest> getDanceTests() {
		return this.danceTests;
	}
	public void setDanceTests(final Collection<DanceTest> danceTests) {
		this.danceTests = danceTests;
	}

	@ElementCollection
	@NotNull
	@ManyToMany(mappedBy = "alumns")
	public Collection<DanceClass> getDanceClasses() {
		return this.danceClasses;
	}
	public void setDanceClasses(final Collection<DanceClass> danceClasses) {
		this.danceClasses = danceClasses;
	}

}
