
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DanceClassService;
import services.DanceSchoolService;
import services.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController extends AbstractController {

	@Autowired
	private TeacherService		teacherService;
	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private DanceClassService	danceClassService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceSchoolId) {
		final ModelAndView res = new ModelAndView("teacher/list");
		res.addObject("teachers", this.teacherService.findTeachersBySchool(danceSchoolId));
		res.addObject("requestURI", "teacher/list.do");
		res.addObject("danceschool", this.danceSchoolService.findOne(danceSchoolId).getName());
		res.addObject("danceSchoolId", danceSchoolId);
		res.addObject("managerTeacher", false);
		return res;

	}

	@RequestMapping(value = "/listByClass", method = RequestMethod.GET)
	public ModelAndView listclasses(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("teacher/list");
		res.addObject("teachers", this.teacherService.findTeachersByClass(classId));
		res.addObject("requestURI", "teacher/listByClass.do");
		res.addObject("danceschool", this.danceClassService.findOne(classId).getDanceSchool().getName());

		return res;

	}
}
