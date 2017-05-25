
package controllers.alumn;

import domain.Alumn;
import domain.DanceTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import services.AlumnService;
import services.DanceClassService;
import services.DanceTestService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/danceTest/alumn")
public class DanceTestAlumnController extends AbstractController {

	@Autowired
	DanceTestService danceTestService;

	@Autowired
	private DanceClassService danceClassService;

	@Autowired
	private AlumnService alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("danceTest/list");

		res.addObject("danceTests", danceTestService.findDanceTestsAvailableByDanceClass(classId));
		res.addObject("myDanceTests", danceTestService.findDanceTestsByAlumn(alumnService.findByPrincipal().getId()));
		res.addObject("requestURI", "danceTest/list.do");
		return res;

	}

	@RequestMapping(value = "/joinin", method = RequestMethod.GET)
	public ModelAndView joinin(@RequestParam int danceTestId){
		ModelAndView res = new ModelAndView("danceTest/list");;
		DanceTest danceTest = danceTestService.findOne(danceTestId);

		try{
			Alumn alumn = alumnService.findByPrincipal();
			Assert.isTrue(danceTest.getLimitInscription().after(new Date()));
			Assert.isTrue(!danceTestService.findDanceTestsByAlumn(alumn.getId()).contains(danceTest) && danceTestService.danceTestsCanJoinIn(alumn.getId()).contains(danceTest));

			danceTestService.joinInDanceTest(danceTestId);

			res.addObject("danceTests", danceTestService.findDanceTestsAvailableByDanceClass(danceTest.getDanceClass().getId()));
			res.addObject("myDanceTests", danceTestService.findDanceTestsByAlumn(alumn.getId()));
			res.addObject("requestURI", "danceTest/list.do");
		}catch (Throwable oops){
			res.addObject("danceTests", danceTestService.findDanceTestsAvailableByDanceClass(danceTest.getDanceClass().getId()));
			res.addObject("myDanceTests", danceTestService.findDanceTestsByAlumn(alumnService.findByPrincipal().getId()));
			res.addObject("requestURI", "danceTest/list.do");
			res.addObject("message", "danceTest.join.error");
		}
		return res;
	}
}
