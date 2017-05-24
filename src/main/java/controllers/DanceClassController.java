
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.Authority;
import security.LoginService;
import services.AlumnService;
import services.DanceClassService;
import services.DanceSchoolService;
import services.ManagerService;

@Controller
@RequestMapping("/danceClass")
public class DanceClassController extends AbstractController {

	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private DanceSchoolService	danceSchoolService;
	@Autowired
	private ManagerService managerService;

	@Autowired
	private AlumnService alumnService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceSchoolId) {
		final ModelAndView res = new ModelAndView("danceClass/list");
		
		res.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(danceSchoolId));
		res.addObject("requestURI", "danceClass/list.do");
		res.addObject("danceschool", this.danceSchoolService.findOne(danceSchoolId));
		res.addObject("danceSchoolId", danceSchoolId);
		res.addObject("myClasses", false);
		try{
			for(Authority a: LoginService.getPrincipal().getAuthorities()){
				if(a.getAuthority().equals("MANAGER")){
					res.addObject("managerPrincipal", managerService.findByPrincipal());
				}else if(a.getAuthority().equals("ALUMN")){
					res.addObject("danceClassesJoinIn", danceClassService.findDanceClassActiveByAlumn(alumnService.findByPrincipal().getId()));
				}
			}
		}catch (Throwable oops){

		}

		return res;

	}

}
