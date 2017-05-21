package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import forms.SearchForm;
import services.DanceSchoolService;
import services.ManagerService;

@Controller
@RequestMapping("/danceSchool/manager")
public class DanceSchoolManagerController extends AbstractController{
	
	@Autowired
	private DanceSchoolService danceSchoolService;

	@Autowired
	private ManagerService managerService;
	
	public DanceSchoolManagerController() {
		super();
	}

	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/listByManager");
		res.addObject("danceSchools", this.danceSchoolService.findAllByManager(managerService.findByPrincipal().getId()));
		res.addObject("requestURI", "danceSchool/manager/list.do");
		res.addObject("searchForm", new SearchForm());
		return res;

	}

}
