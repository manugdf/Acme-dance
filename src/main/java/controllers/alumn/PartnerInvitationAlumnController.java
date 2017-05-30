
package controllers.alumn;

import java.util.Collection;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Alumn;
import domain.PartnerInvitation;
import services.AlumnService;
import services.PartnerInvitationService;

@Controller
@RequestMapping("/partnerInvitation/alumn")
public class PartnerInvitationAlumnController extends AbstractController {

	@Autowired
	private PartnerInvitationService	partnerInvitationService;

	@Autowired
	private AlumnService				alumnService;


	@RequestMapping(value = "/listPartners", method = RequestMethod.GET)
	public ModelAndView listPartners() {
		final ModelAndView res = new ModelAndView("partnerInvitation/listPartners");
		final Alumn alumn = this.alumnService.findByPrincipal();

		final Collection<PartnerInvitation> sended = this.partnerInvitationService.findSendedAndAcceptedByAlumn(alumn.getId());
		final Collection<PartnerInvitation> received = this.partnerInvitationService.findReceivedAndAcceptedByAlumn(alumn.getId());

		received.addAll(sended);

		res.addObject("partnerInvitations", received);
		res.addObject("requestURI", "partnerInvitation/alumn/listPartners.do");
		res.addObject("received", true);
		return res;

	}

	@RequestMapping(value = "/listReceived", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("partnerInvitation/list");
		final Alumn alumn = this.alumnService.findByPrincipal();

		final Collection<PartnerInvitation> inv = this.partnerInvitationService.findReceivedByAlumn(alumn.getId());
		res.addObject("partnerInvitations", inv);
		res.addObject("requestURI", "partnerInvitation/alumn/listReceived.do");
		res.addObject("received", true);
		return res;

	}

	@RequestMapping(value = "/listSended", method = RequestMethod.GET)
	public ModelAndView listSended() {
		final ModelAndView res = new ModelAndView("partnerInvitation/list");
		final Alumn alumn = this.alumnService.findByPrincipal();

		final Collection<PartnerInvitation> inv = this.partnerInvitationService.findSendedByAlumn(alumn.getId());
		res.addObject("partnerInvitations", inv);
		res.addObject("requestURI", "partnerInvitation/alumn/listSended.do");
		res.addObject("received", false);
		return res;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int alumnId) {
		final ModelAndView res = new ModelAndView("partnerInvitation/create");
		final PartnerInvitation inv = this.partnerInvitationService.create(alumnId);
		Assert.notNull(inv);
		res.addObject("partnerInvitation", inv);
		res.addObject("requestURI", "partnerInvitation/alumn/create.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final PartnerInvitation partnerInvitation, final BindingResult binding) {

		ModelAndView res = new ModelAndView();

		if (binding.hasErrors()) {
			res = new ModelAndView("partnerInvitation/create");
			res.addObject("requestURI", "partnerInvitation/alumn/create.do");
			res.addObject("partnerInvitation", partnerInvitation);

		} else
			try {
				this.partnerInvitationService.save(partnerInvitation);
				res = new ModelAndView("partnerInvitation/list");
				final Alumn alumn = this.alumnService.findByPrincipal();

				final Collection<PartnerInvitation> inv = this.partnerInvitationService.findSendedByAlumn(alumn.getId());
				res.addObject("partnerInvitations", inv);
				res.addObject("requestURI", "partnerInvitation/alumn/listSended.do");
				res.addObject("received", false);
			} catch (final ConstraintViolationException oops) {
				res = new ModelAndView("partnerInvitation/create");
				res.addObject("partnerInvitation", partnerInvitation);
				res.addObject("message", "partnerInvitation.commit.error");
			}
		return res;

	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int invitationId) {

		final ModelAndView res = new ModelAndView("partnerInvitation/list");
		final Alumn alumn = this.alumnService.findByPrincipal();

		final Collection<PartnerInvitation> inv = this.partnerInvitationService.findReceivedByAlumn(alumn.getId());
		res.addObject("partnerInvitations", inv);
		res.addObject("requestURI", "partnerInvitation/alumn/listReceived.do");
		res.addObject("received", true);
		try {

			final PartnerInvitation pi = this.partnerInvitationService.findOne(invitationId);
			Assert.isTrue(alumn.getId() == (pi.getInvitationReceiver().getId()));

			Assert.isTrue(pi.getState().equals("PENDING"));
			pi.setState("ACCEPTED");
			this.partnerInvitationService.save(pi);
		} catch (final Throwable oops) {
			res.addObject("message", "partnerInvitation.error.accepted");
		}

		return res;

	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int invitationId) {

		final ModelAndView res = new ModelAndView("partnerInvitation/list");
		final Alumn alumn = this.alumnService.findByPrincipal();

		final Collection<PartnerInvitation> inv = this.partnerInvitationService.findReceivedByAlumn(alumn.getId());
		res.addObject("partnerInvitations", inv);
		res.addObject("requestURI", "partnerInvitation/alumn/listReceived.do");
		res.addObject("received", true);
		try {

			final PartnerInvitation pi = this.partnerInvitationService.findOne(invitationId);
			Assert.isTrue(pi.getState().equals("PENDING"));
			pi.setState("REJECTED");
			this.partnerInvitationService.save(pi);
		} catch (final Throwable oops) {
			res.addObject("message", "partnerInvitation.error.rejected");
		}

		return res;

	}

}
