
package controllers.alumn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import forms.SearchForm;
import services.AlumnService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceSchool/alumn")
public class DanceSchoolAlumnController extends AbstractController {

	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private AlumnService		alumnService;


	public DanceSchoolAlumnController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/list");
		res.addObject("danceSchools", this.danceSchoolService.findSchoolsByAlumn(this.alumnService.findByPrincipal().getId()));
		res.addObject("requestURI", "danceSchool/alumn/list.do");
		res.addObject("searchForm", new SearchForm());
		res.addObject("partnerview", true);
		res.addObject("isManager", false);
		res.addObject("chooseSchool", "danceSchool.choose");
		return res;

	}

}
