
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.DanceClassService;
import services.ManagerService;
import services.ScheduleService;
import controllers.AbstractController;
import domain.DanceClass;
import domain.Schedule;

@Controller
@RequestMapping("manager/schedule")
public class ScheduleManagerController extends AbstractController {

	@Autowired
	private ScheduleService		scheduleService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private DanceClassService	danceClassService;


	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("schedule/create");

		final Schedule s = this.scheduleService.create();

		final DanceClass d = this.danceClassService.findOne(classId);
		s.setDanceClass(d);

		res.addObject("schedule", s);
		res.addObject("requestUri", "manager/schedule/create.do");

		return res;

	}

	@RequestMapping(value = "/create", params = "save")
	public ModelAndView create(Schedule schedule, final BindingResult binding) {
		ModelAndView res = new ModelAndView();

		schedule = this.scheduleService.reconstruct(schedule, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res = new ModelAndView("schedule/create");
			res.addObject("schedule", schedule);
		} else
			this.scheduleService.save(schedule);

		return res;

	}
}
