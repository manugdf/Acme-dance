
package controllers.alumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import services.DanceClassService;

@Controller
@RequestMapping("/danceTest/alumn")
public class DanceTestAlumnController extends AbstractController {

	@Autowired
	private DanceClassService danceClassService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("danceTest/list");
		final DanceClass dc = this.danceClassService.findOne(classId);

		res.addObject("danceTests", dc.getDanceTests());
		res.addObject("requestURI", "danceTest/list.do");
		return res;

	}
}
