
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceSchool;
import forms.SearchForm;
import services.DanceSchoolService;
import services.ManagerService;

@Controller
@RequestMapping("/danceSchool/manager")
public class DanceSchoolManagerController extends AbstractController {

	@Autowired
	private DanceSchoolService	danceSchoolService;

	@Autowired
	private ManagerService		managerService;


	public DanceSchoolManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/listByManager");
		res.addObject("danceSchools", this.danceSchoolService.findAllByManager(this.managerService.findByPrincipal().getId()));
		res.addObject("requestURI", "danceSchool/manager/list.do");
		res.addObject("searchForm", new SearchForm());
		return res;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("danceSchool/create");
		final DanceSchool danceSchool = this.danceSchoolService.create();
		res.addObject("danceSchool", danceSchool);
		res.addObject("requestURI", "danceSchool/manager/create.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(DanceSchool danceSchool, final BindingResult binding) {

		ModelAndView res = new ModelAndView();

		danceSchool = this.danceSchoolService.reconstruct(danceSchool, binding);
		if (binding.hasErrors()) {
			res = new ModelAndView("danceSchool/create");
			res.addObject("requestURI", "danceSchool/manager/create.do");
			res.addObject("danceSchool", danceSchool);

		} else {

			this.danceSchoolService.newDanceSchool(danceSchool);
			res = this.list();
		}
		return res;

	}

}
