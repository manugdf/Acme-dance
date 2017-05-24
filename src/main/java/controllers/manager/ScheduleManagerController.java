
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
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
import forms.ScheduleForm;

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

		final ScheduleForm s = new ScheduleForm();

		final DanceClass d = this.danceClassService.findOne(classId);
		s.setDanceClass(d);

		res.addObject("scheduleForm", s);
		res.addObject("create", true);
		res.addObject("requestUri", "manager/schedule/create.do");

		return res;

	}

	@RequestMapping(value = "/create", params = "save")
	public ModelAndView create(@Valid final ScheduleForm schedule, final BindingResult binding) {
		ModelAndView res = new ModelAndView("schedule/create");

		final Schedule schedul = this.scheduleService.reconstruct(schedule, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res.addObject("schedule", schedule);
		} else if (schedule.getStartDate().after(schedule.getEndTime())) {
			res.addObject("schedule", schedule);
			res.addObject("message", "schedule.error.date");
		} else
			try {

				this.scheduleService.createSchedule(schedul);

				res = new ModelAndView("redirect:/schedule/list.do?classId=" + schedul.getDanceClass().getId());
			} catch (final Exception e) {

				System.out.println(e);
				res.addObject("schedule", schedule);
				res.addObject("message", "alumn.commit.error");
			}
		return res;

	}

	@RequestMapping("/edit")
	public ModelAndView edit(@RequestParam final int scheduleId) {
		final ModelAndView res = new ModelAndView("schedule/edit");

		final Schedule schedule = this.scheduleService.findOne(scheduleId);
		final ScheduleForm s = this.scheduleService.createBySchedule(schedule);

		Assert.isTrue(s.getDanceClass().getDanceSchool().getManager().equals(this.managerService.findByPrincipal()));

		res.addObject("scheduleForm", s);
		res.addObject("requestUri", "manager/schedule/edit.do");
		res.addObject("create", false);

		return res;

	}

	@RequestMapping(value = "/edit", params = "save")
	public ModelAndView edit(final ScheduleForm schedule, final BindingResult binding) {
		ModelAndView res = new ModelAndView("schedule/edit");

		try {
			final Schedule schedul = this.scheduleService.reconstruct(schedule, binding);

			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				res.addObject("schedule", schedule);
			} else if (schedule.getStartDate().after(schedule.getEndTime())) {
				res.addObject("schedule", schedule);
				res.addObject("message", "schedule.error.date");
			} else

				this.scheduleService.createSchedule(schedul);

			res = new ModelAndView("redirect:/schedule/list.do?classId=" + schedul.getDanceClass().getId());
		} catch (final Exception e) {

			System.out.println(e);
			res.addObject("schedule", schedule);
			res.addObject("message", "alumn.commit.error");
		}
		return res;

	}
}
