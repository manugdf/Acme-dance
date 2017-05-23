package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import domain.DanceTest;
import services.AlumnService;
import services.DanceTestService;

@Controller
@RequestMapping("/alumn/teacher")
public class AlumnTeacherController extends AbstractController{
	
	@Autowired
	private AlumnService alumnService;
	@Autowired
	private DanceTestService danceTestService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceTestId) {
		ModelAndView res = new ModelAndView("alumn/list");
		DanceTest danceTest=danceTestService.findOne(danceTestId);

		res.addObject("alumns", danceTest.getAlumns());
		res.addObject("requestURI", "alumn/teacher/list.do");
		return res;
	}

}
