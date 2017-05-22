
package controllers.alumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Alumn;
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
}
