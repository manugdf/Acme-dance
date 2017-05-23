package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.DanceClassService;
import services.TeacherService;

@Controller
@RequestMapping("/danceClass/teacher")
public class DanceClassTeacherController extends AbstractController {
	
	// Services
	@Autowired
	private DanceClassService danceClassService;
	@Autowired
	private TeacherService teacherService;

	// Constructor
	public DanceClassTeacherController() {
		super();
	}
	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceClass/listByTeacher");
		
		res.addObject("danceClasses", teacherService.findByPrincipal().getDanceClasses());
		res.addObject("requestURI", "danceClass/teacher/list.do");
		res.addObject("myClasses", false);
		return res;

	}


}
