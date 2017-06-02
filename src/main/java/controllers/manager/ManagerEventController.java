
package controllers.manager;

import java.util.Calendar;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceSchool;
import domain.Event;
import domain.Manager;
import services.DanceSchoolService;
import services.EventService;
import services.ManagerService;

@Controller
@RequestMapping("/mngr/event")
public class ManagerEventController extends AbstractController {

	@Autowired
	private EventService		eventService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private DanceSchoolService	danceSchoolService;


	@RequestMapping("/list")
	public ModelAndView list(@RequestParam final int danceSchoolId) {
		final ModelAndView res = new ModelAndView("event/list");

		final DanceSchool d = this.danceSchoolService.findOne(danceSchoolId);
		final Collection<Event> events = this.eventService.eventsByDanceSchoolId(danceSchoolId);

		final Manager logged = this.managerService.findByPrincipal();

		res.addObject("events", events);
		res.addObject("logged", logged);
		res.addObject("danceSchool", d);
		res.addObject("requestURI", "mngr/event/list.do");
		return res;
	}

	@RequestMapping("/create")
	public ModelAndView create(@RequestParam final int danceSchoolId) {
		final ModelAndView res = new ModelAndView("event/create");

		final DanceSchool d = this.danceSchoolService.findOne(danceSchoolId);
		Assert.isTrue(d.getManager().equals(this.managerService.findByPrincipal()));

		final Event e = this.eventService.create();
		e.setDanceSchool(d);

		res.addObject("requestUri", "mngr/event/create.do");
		res.addObject("event", e);
		res.addObject("danceSchoolId", d.getId());

		return res;
	}

	@RequestMapping(value = "/create", params = "save")
	public ModelAndView create(Event event, final BindingResult binding) {
		ModelAndView res = new ModelAndView("event/create");

		event = this.eventService.reconstruct(event, binding);

		if (binding.hasErrors()) {
			res.addObject("event", event);
			res.addObject("requestUri", "mngr/event/create.do");
		} else if (event.getStartDate().before(Calendar.getInstance().getTime())) {
			res.addObject("event", event);
			res.addObject("message", "event.date.error");
		} else
			try {
				event = this.eventService.newOrEditEvent(event);
				res = new ModelAndView("redirect:/mngr/event/list.do?danceSchoolId=" + event.getDanceSchool().getId());
			} catch (final Exception e) {
				res.addObject("event", event);
				res.addObject("message", "alumn.commit.error");
			}
		return res;
	}
	@RequestMapping(value = "/edit")
	public ModelAndView edit(@RequestParam final int eventId) {
		final ModelAndView res = new ModelAndView("event/edit");

		final Event event = this.eventService.findOne(eventId);
		Assert.isTrue(event.getDanceSchool().getManager().equals(this.managerService.findByPrincipal()));

		res.addObject("requestUri", "mngr/event/edit.do");
		res.addObject("event", event);

		return res;
	}

	@RequestMapping(value = "/edit", params = "save")
	public ModelAndView edit(Event event, final BindingResult binding) {
		ModelAndView res = new ModelAndView("event/edit");

		final Event eve = this.eventService.reconstruct(event, binding);

		if (binding.hasErrors()) {
			res.addObject("event", eve);
			res.addObject("requestUri", "mngr/event/edit.do");
		} else if (event.getStartDate().before(Calendar.getInstance().getTime())) {
			res.addObject("event", eve);
			res.addObject("message", "event.date.error");
		} else
			try {
				event = this.eventService.newOrEditEvent(eve);
				res = new ModelAndView("redirect:/mngr/event/list.do?danceSchoolId=" + eve.getDanceSchool().getId());
			} catch (final Exception e) {
				res.addObject("event", eve);
				res.addObject("message", "alumn.commit.error");
			}
		return res;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int eventId) {

		final Event event = this.eventService.findOne(eventId);
		ModelAndView res = this.list(event.getDanceSchool().getId());

		try {
			if (event.getAlumns().size() == 0) {
				this.eventService.delete(event);
				res = this.list(event.getDanceSchool().getId());
				res.addObject("message", "event.deleted");
			} else
				res.addObject("message", "event.delete.alumnError");
		} catch (final Throwable oops) {

			res.addObject("message", "event.delete.error");
		}
		return res;
	}
}
