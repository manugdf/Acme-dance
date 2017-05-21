
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController extends AbstractController {

	@Autowired
	private ScheduleService scheduleService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int classId) {
		final ModelAndView res = new ModelAndView("schedule/list");
		res.addObject("schedules", this.scheduleService.findScheduleByClass(classId));
		res.addObject("requestURI", "schedule/list.do");
		return res;

	}
}
