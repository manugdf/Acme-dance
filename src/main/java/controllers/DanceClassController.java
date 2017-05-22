
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DanceClassService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceClass")
public class DanceClassController extends AbstractController {

	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private DanceSchoolService	danceSchoolService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceSchoolId) {
		final ModelAndView res = new ModelAndView("danceClass/list");
		res.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(danceSchoolId));
		res.addObject("requestURI", "danceClass/list.do");
		res.addObject("danceschool", this.danceSchoolService.findOne(danceSchoolId));
		return res;

	}
}
