
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.AdminService;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceSchool/administrator")
public class DanceSchoolAdministratorController extends AbstractController {

	@Autowired
	private DanceSchoolService	danceSchoolService;

	@Autowired
	private AdminService		adminService;


	public DanceSchoolAdministratorController() {
		super();
	}

	@RequestMapping(value = "/listPending", method = RequestMethod.GET)
	public ModelAndView list() {

		this.adminService.checkLoggedIsAdmin();

		final ModelAndView res = new ModelAndView("danceSchool/listPending");
		res.addObject("danceSchools", this.danceSchoolService.findAllPending());
		res.addObject("requestURI", "danceSchool/administrator/listPending.do");
		return res;
	}

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int id) {

		this.adminService.checkLoggedIsAdmin();
		this.danceSchoolService.rejectDanceSchool(id);
		final ModelAndView res = new ModelAndView("redirect:listPending.do");

		return res;

	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int id) {

		this.adminService.checkLoggedIsAdmin();
		this.danceSchoolService.acceptDanceSchool(id);
		final ModelAndView res = new ModelAndView("redirect:listPending.do");

		return res;

	}

}
