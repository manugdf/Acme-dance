
package controllers.alumn;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Alumn;
import domain.DanceSchool;
import domain.Event;
import services.AlumnService;
import services.DanceSchoolService;
import services.EventService;

@Controller
@RequestMapping("/event/alumn")
public class EventAlumnController extends AbstractController {

	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private EventService		eventService;
	@Autowired
	private AlumnService		alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int id) {
		final ModelAndView res = new ModelAndView("event/list");
		final DanceSchool dancesc = this.danceSchoolService.findOne(id);
		boolean inSchool = false;
		res.addObject("events", dancesc.getEvents());
		res.addObject("requestURI", "event/list.do");
		res.addObject("danceschool", dancesc.getName());

		final Alumn alumn = this.alumnService.findByPrincipal();
		final Collection<DanceSchool> alumnSchools = this.danceSchoolService.findSchoolsByAlumn(alumn.getId());
		if (alumnSchools.contains(dancesc))
			inSchool = true;

		res.addObject("inSchool", inSchool);
		res.addObject("alumnId", alumn.getId());
		res.addObject("idSchool", dancesc.getId());

		return res;

	}

	@RequestMapping(value = "/assist", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int idEvent, @RequestParam final int idSchool) {

		final ModelAndView res = new ModelAndView("event/list");

		try {
			final Event event = this.eventService.findOne(idEvent);
			final Alumn alumn = this.alumnService.findByPrincipal();
			Assert.isTrue(!event.getAlumns().contains(alumn));

			event.getAlumns().add(alumn);
			this.eventService.save(event);
			final DanceSchool dancesc = this.danceSchoolService.findOne(idSchool);
			boolean inSchool = false;
			res.addObject("events", dancesc.getEvents());
			res.addObject("requestURI", "event/alumn/list.do");
			res.addObject("danceschool", dancesc.getName());
			res.addObject("logged", alumn);

			final Collection<DanceSchool> alumnSchools = this.danceSchoolService.findSchoolsByAlumn(alumn.getId());
			if (alumnSchools.contains(dancesc))
				inSchool = true;

			res.addObject("inSchool", inSchool);
			res.addObject("alumnId", alumn.getId());
			res.addObject("idSchool", dancesc.getId());
		} catch (final Throwable oops) {
			final DanceSchool dancesc = this.danceSchoolService.findOne(idSchool);
			final Alumn alumn = this.alumnService.findByPrincipal();
			boolean inSchool = false;
			res.addObject("events", dancesc.getEvents());
			res.addObject("requestURI", "event/alumn/list.do");
			res.addObject("danceschool", dancesc.getName());
			res.addObject("logged", alumn);

			final Collection<DanceSchool> alumnSchools = this.danceSchoolService.findSchoolsByAlumn(alumn.getId());
			if (alumnSchools.contains(dancesc))
				inSchool = true;

			res.addObject("inSchool", inSchool);
			res.addObject("alumnId", alumn.getId());
			res.addObject("idSchool", dancesc.getId());
			res.addObject("message", "event.commit.error");
			System.out.println(oops.getMessage());
		}
		return res;
	}

}
