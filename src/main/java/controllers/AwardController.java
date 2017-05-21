
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/award")
public class AwardController extends AbstractController {

	@Autowired
	private AwardService		awardService;
	@Autowired
	private DanceSchoolService	danceSchoolService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int schoolId) {
		final ModelAndView res = new ModelAndView("award/list");
		res.addObject("awards", this.awardService.findAwardBySchool(schoolId));
		res.addObject("requestURI", "award/list.do");
		res.addObject("danceschool", this.danceSchoolService.findOne(schoolId).getName());

		return res;

	}

}
