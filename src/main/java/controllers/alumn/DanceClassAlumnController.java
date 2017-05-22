
package controllers.alumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Alumn;
import services.AlumnService;
import services.DanceClassService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceClass/alumn")
public class DanceClassAlumnController {

	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private AlumnService		alumnService;


	@RequestMapping(value = "/listMyClasses", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceClass/listMyClasses");
		final Alumn alumn = this.alumnService.findByPrincipal();
		res.addObject("danceClasses", alumn.getDanceClasses());
		res.addObject("requestURI", "danceClass/listMyClasses.do");
		return res;

	}
}
