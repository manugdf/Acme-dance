
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Alumn;
import domain.CreditCard;
import domain.DanceCertificate;
import domain.DanceClass;
import domain.DanceTest;
import domain.Event;
import domain.Message;
import domain.PartnerInvitation;
import domain.PartnerRequest;
import domain.Payment;
import domain.Review;
import forms.AlumnForm;
import repositories.AlumnRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class AlumnService {

	//Repository------------------------------------------------------------------

	@Autowired
	private AlumnRepository alumnRepository;


	//CRUD Methods----------------------------------------------------------------

	public Alumn create() {

		final UserAccount userAccount = new UserAccount();
		final Authority aut = new Authority();
		aut.setAuthority(Authority.TEACHER);
		final Collection<Authority> authorities = userAccount.getAuthorities();
		authorities.add(aut);
		userAccount.setAuthorities(authorities);

		final Alumn alumn = new Alumn();
		alumn.setUserAccount(userAccount);
		alumn.setEvents(new ArrayList<Event>());
		alumn.setReviews(new ArrayList<Review>());
		alumn.setDanceCertificates(new ArrayList<DanceCertificate>());
		alumn.setDanceClasses(new ArrayList<DanceClass>());
		alumn.setMessagesReceived(new ArrayList<Message>());
		alumn.setMessagesSended(new ArrayList<Message>());
		alumn.setDanceTests(new ArrayList<DanceTest>());
		alumn.setPartnerInvitationReceives(new ArrayList<PartnerInvitation>());
		alumn.setPartnerRequests(new ArrayList<PartnerRequest>());
		alumn.setPartnerInvitationSends(new ArrayList<PartnerInvitation>());
		alumn.setFee(0.0);
		alumn.setPayments(new ArrayList<Payment>());

		return alumn;

	}

	public Alumn findOne(final int id) {
		Alumn alumn;
		alumn = this.alumnRepository.findOne(id);
		Assert.notNull(alumn);
		return alumn;
	}

	public Collection<Alumn> findAll() {
		Collection<Alumn> alumns;
		alumns = this.alumnRepository.findAll();
		return alumns;

	}

	public Alumn save(final Alumn alumn) {
		Assert.notNull(alumn);
		final Alumn saved = this.alumnRepository.save(alumn);
		return saved;
	}

	public void delete(final Alumn alumn) {
		this.alumnRepository.delete(alumn);
	}

	//Other Methods--------------------------------------------------------------

	public Alumn findByPrincipal() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final Alumn alumn = this.alumnRepository.findByPrincipal(userAccount.getId());
		Assert.isTrue(alumn.getUserAccount().equals(userAccount));
		return alumn;
	}

	public void register(final Alumn alumn) {
		Assert.notNull(alumn);
		UserAccount userAccount;
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		userAccount = alumn.getUserAccount();
		userAccount.setPassword(encoder.encodePassword(userAccount.getPassword(), null));
		alumn.setUserAccount(userAccount);

		this.save(alumn);
	}

	public Alumn reconstruct(final AlumnForm alumnForm) {
		final Alumn alumn = this.create();
		alumn.getUserAccount().setUsername(alumnForm.getUsername());
		alumn.getUserAccount().setPassword(alumnForm.getPassword());
		alumn.setName(alumnForm.getName());
		alumn.setSurname(alumnForm.getSurname());
		alumn.setEmail(alumnForm.getEmail());
		alumn.setPhone(alumnForm.getPhone());

		final CreditCard creditcard = new CreditCard();
		creditcard.setBrandName(alumnForm.getBrandName());
		creditcard.setCvvCode(alumnForm.getCvvCode());
		creditcard.setExpirationMonth(alumnForm.getExpirationMonth());
		creditcard.setExpirationYear(alumnForm.getExpirationYear());
		creditcard.setHolderName(alumnForm.getHolderName());
		creditcard.setNumber(alumnForm.getNumber());

		alumn.setCreditCard(creditcard);

		return alumn;
	}

}
