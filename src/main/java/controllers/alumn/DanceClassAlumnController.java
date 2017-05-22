
package controllers.alumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Alumn;
import domain.DanceClass;
import services.AlumnService;
import services.DanceClassService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceClass/alumn")
public class DanceClassAlumnController {

	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private AlumnService		alumnService;


	@RequestMapping(value = "/listMyClasses", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceClass/list");
		final Alumn alumn = this.alumnService.findByPrincipal();
		res.addObject("danceClasses", alumn.getDanceClasses());
		res.addObject("requestURI", "danceClass/listMyClasses.do");
		res.addObject("myClasses", true);
		return res;

	}

	@RequestMapping(value = "/quit", method = RequestMethod.GET)
	public ModelAndView save(@RequestParam final int classId) {

		final ModelAndView res = new ModelAndView("danceClass/list");

		try {
			final DanceClass dc = this.danceClassService.findOne(classId);
			final Alumn al = this.alumnService.findByPrincipal();
			Assert.isTrue(dc.getAlumns().contains(al));
			dc.getAlumns().remove(al);
			this.danceClassService.save(dc);
			res.addObject("danceClasses", al.getDanceClasses());
			res.addObject("requestURI", "danceClass/listMyClasses.do");
			res.addObject("myClasses", true);
		} catch (final Throwable oops) {
			final Alumn alumn = this.alumnService.findByPrincipal();
			res.addObject("danceClasses", alumn.getDanceClasses());
			res.addObject("requestURI", "danceClass/listMyClasses.do");
			res.addObject("myClasses", true);
			res.addObject("message", "danceclass.commit.error");
			System.out.println(oops.getMessage());
		}
		return res;
	}
}
