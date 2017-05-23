
package controllers.alumn;

import java.util.ArrayList;
import java.util.Collection;

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
import domain.PartnerRequest;
import services.AlumnService;
import services.PartnerRequestService;

@Controller
@RequestMapping("/partnerRequest/alumn")
public class PartnerRequestController extends AbstractController {

	@Autowired
	private PartnerRequestService	partnerRequestService;

	@Autowired
	private AlumnService			alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("partnerRequest/list");
		final Alumn alumn = this.alumnService.findByPrincipal();

		res.addObject("partnerRequests", alumn.getPartnerRequests());
		res.addObject("requestURI", "partnerRequest/alumn/list.do");
		return res;

	}

	//	@RequestMapping(value = "/list", method = RequestMethod.GET)
	//	public ModelAndView listFromSchool() {
	//		final ModelAndView res = new ModelAndView("partnerRequest/list");
	//		final Alumn alumn = this.alumnService.findByPrincipal();
	//		//Me traigo las danceSchools a las que estoy apuntado, y de cada una me traigo sus alumnos, y los que tengan Partner Request los meto en la lista
	//		Collection<DanceSchool> danceSchools =
	//		res.addObject("partnerRequests", alumn.getPartnerRequests());
	//		res.addObject("requestURI", "partnerRequest/alumn/list.do");
	//		return res;
	//
	//	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("partnerRequest/create");
		final PartnerRequest pr = this.partnerRequestService.create();
		res.addObject("partnerRequest", pr);
		res.addObject("requestURI", "partnerRequest/alumn/create.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final PartnerRequest partnerRequest, final BindingResult binding) {

		ModelAndView res = new ModelAndView();

		if (binding.hasErrors()) {
			final PartnerRequest pr = this.partnerRequestService.create();
			res = new ModelAndView("event/create");
			res.addObject("requestURI", "artnerRequest/alumn/create.do");
			res.addObject("partnerRequest", pr);

		} else {
			this.partnerRequestService.save(partnerRequest);
			res = new ModelAndView("partnerRequest/list");
			final Alumn alumn = this.alumnService.findByPrincipal();
			res.addObject("partnerRequests", alumn.getPartnerRequests());
			res.addObject("requestURI", "partnerRequest/alumn/list.do");

		}
		return res;

	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int partnerRequestId) {

		ModelAndView res = new ModelAndView();
		final Alumn alumn = this.alumnService.findByPrincipal();

		try {
			final PartnerRequest pr = this.partnerRequestService.findOne(partnerRequestId);
			Assert.isTrue(pr.getAlumn().getId() == this.alumnService.findByPrincipal().getId());
			this.partnerRequestService.delete(pr);
			res = new ModelAndView("partnerRequest/list");

			res.addObject("partnerRequests", alumn.getPartnerRequests());
			res.addObject("requestURI", "partnerRequest/alumn/list.do");
		} catch (final Throwable oops) {
			res = new ModelAndView("partnerRequest/list");
			res.addObject("partnerRequests", alumn.getPartnerRequests());
			res.addObject("requestURI", "partnerRequest/alumn/list.do");
			res.addObject("message", "partnerRequest.commit.error");
		}
		return res;

	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam final int schoolId) {
		final ModelAndView res = new ModelAndView("partnerRequest/list");
		final Alumn alumn = this.alumnService.findByPrincipal();
		final Collection<Alumn> alumns = this.alumnService.findAlumnsBySchoolId(schoolId);

		if (alumns.contains(alumn))
			alumns.remove(alumn);
		final Collection<PartnerRequest> pr = new ArrayList<PartnerRequest>();
		for (final Alumn a : alumns)
			pr.addAll(a.getPartnerRequests());
		res.addObject("partnerRequests", pr);
		res.addObject("requestURI", "partnerRequest/alumn/view.do");
		return res;

	}

}
