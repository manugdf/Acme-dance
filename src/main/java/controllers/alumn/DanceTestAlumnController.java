
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
import domain.DanceTest;
import domain.Payment;
import services.AlumnService;
import services.DanceClassService;
import services.DanceTestService;

@Controller
@RequestMapping("/danceTest/alumn")
public class DanceTestAlumnController extends AbstractController {

	@Autowired
	DanceTestService			danceTestService;

	@Autowired
	private DanceClassService	danceClassService;

	@Autowired
	private AlumnService		alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("danceTest/list");

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
			res.addObject("danceTests", this.danceTestService.findDanceTestsAvailableByDanceClass(classId));
			res.addObject("myDanceTests", this.danceTestService.findDanceTestsByAlumn(this.alumnService.findByPrincipal().getId()));
			res.addObject("requestURI", "danceTest/list.do");
		} catch (final Throwable oops) {
			res.addObject("message", "danceTest.payment.error");
		}
		return res;

	}

	@RequestMapping(value = "/joinin", method = RequestMethod.GET)
	public ModelAndView joinin(@RequestParam final int danceTestId) {
		final ModelAndView res = new ModelAndView("danceTest/list");
		;
		final DanceTest danceTest = this.danceTestService.findOne(danceTestId);

		try {
			final Alumn alumn = this.alumnService.findByPrincipal();
			Assert.isTrue(danceTest.getLimitInscription().after(new Date()));
			Assert.isTrue(!this.danceTestService.findDanceTestsByAlumn(alumn.getId()).contains(danceTest) && this.danceTestService.danceTestsCanJoinIn(alumn.getId()).contains(danceTest));

			this.danceTestService.joinInDanceTest(danceTestId);

			res.addObject("danceTests", this.danceTestService.findDanceTestsAvailableByDanceClass(danceTest.getDanceClass().getId()));
			res.addObject("myDanceTests", this.danceTestService.findDanceTestsByAlumn(alumn.getId()));
			res.addObject("requestURI", "danceTest/list.do");
		} catch (final Throwable oops) {
			res.addObject("danceTests", this.danceTestService.findDanceTestsAvailableByDanceClass(danceTest.getDanceClass().getId()));
			res.addObject("myDanceTests", this.danceTestService.findDanceTestsByAlumn(this.alumnService.findByPrincipal().getId()));
			res.addObject("requestURI", "danceTest/list.do");
			res.addObject("message", "danceTest.join.error");
		}
		return res;
	}
}
