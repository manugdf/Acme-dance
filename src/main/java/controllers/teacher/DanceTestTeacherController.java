
package controllers.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import domain.DanceTest;
import services.DanceClassService;
import services.DanceTestService;
import services.TeacherService;

@Controller
@RequestMapping("/danceTest/teacher")
public class DanceTestTeacherController extends AbstractController {

	@Autowired
	private DanceTestService	danceTestService;
	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private TeacherService		teacherService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceClassId) {
		final ModelAndView res = new ModelAndView("danceTest/list");
		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);

		res.addObject("danceTests", danceClass.getDanceTests());
		res.addObject("requestURI", "danceTest/teacher/list.do");
		return res;

	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int danceClassId) {
		ModelAndView res;

		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		Assert.notNull(danceClass);

		res = new ModelAndView("danceTest/create");
		res.addObject("danceTest", this.danceTestService.create());
		res.addObject("requestURI", "danceTest/teacher/create.do?danceClassId=" + danceClassId);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int danceClassId, DanceTest danceTest, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("danceTest/create");

		danceTest = this.danceTestService.reconstruct(danceTest, danceClassId, bindingResult);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res.addObject("requestURI", "danceTest/teacher/create.do?danceClassId=" + danceClassId);
			res.addObject("danceTest", danceTest);
			res.addObject("danceClassId", danceClassId);
		} else
			try {
				danceTestService.createDanceTest(danceTest, danceClassId);
				DanceClass danceClass = this.danceClassService.findOne(danceClassId);

				res = this.list(danceClassId);
				res.addObject("requestURI", "danceTest/teacher/create.do?danceClassId=" + danceClassId);
				res.addObject("danceClasses", this.teacherService.findByPrincipal().getDanceClasses());
				res.addObject("danceTests", danceClass.getDanceTests());
			} catch (final Throwable oops) {
				res.addObject("requestURI", "danceTest/teacher/create.do?danceClassId=" + danceClassId);
				res.addObject("danceTest", danceTest);
			}
		return res;
	}

}
