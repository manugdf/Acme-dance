
package controllers.alumn;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Alumn;
import domain.DanceClass;
import domain.Payment;
import services.AlumnService;
import services.DanceClassService;
import services.MaterialService;

@Controller
@RequestMapping("/material/alumn")
public class MaterialAlumnController extends AbstractController {

	@Autowired
	private MaterialService		materialService;
	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private AlumnService		alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int classId) {

		final ModelAndView res = new ModelAndView("material/list");
		try {
			final DanceClass dc = this.danceClassService.findOne(classId);
			final Alumn alumn = this.alumnService.findByPrincipal();
			boolean paid = true;
			final Date today = new Date(System.currentTimeMillis() - 10000);
			final Collection<Payment> payments = alumn.getPayments();
			for (final Payment p : payments)
				if (p.getEndDate().getTime() < today.getTime() && p.getDanceClass().getId() == classId)
					paid = false;
			Assert.isTrue(paid);
			res.addObject("materials", dc.getMaterials());
			res.addObject("requestURI", "material/alumn/list.do");
			return res;
		} catch (final Throwable oops) {
			res.addObject("message", "material.payment.error");
		}

		return res;
	}
}
