
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.DanceSchoolService;

@Controller
@RequestMapping("/danceSchool")
public class DanceSchoolController extends AbstractController {

	@Autowired
	private DanceSchoolService danceSchoolService;


	public DanceSchoolController() {
		super();
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/list");
		res.addObject("danceSchools", this.danceSchoolService.findAll());
		res.addObject("requestURI", "danceSchool/listAll.do");
		return res;

	}
}
