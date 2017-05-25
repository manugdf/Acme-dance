
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;

@Controller
@RequestMapping("/dashboard/administrator")
public class DashboardAdministratorController {

	//Services
	@Autowired
	private AdminService	adminService;


	//Dashboard
	@RequestMapping("")
	public ModelAndView dashboard() {
		final ModelAndView res = new ModelAndView("administrator/dashboard");

		res.addObject("managerMoreDanceSchoolAccepted", adminService.managerMoreDanceSchoolAccepted());
		res.addObject("acceptedDeniedRatio", adminService.acceptedDeniedRatio());
		res.addObject("bestRating", adminService.bestRating());
		res.addObject("messagesUsersRatio", adminService.messagesUsersRatio());
		res.addObject("actorMoreMessageSend", adminService.actorMoreMessageSend());
		res.addObject("alumnsMoreClasses", adminService.alumnsMoreClasses());
		res.addObject("managerMoreBannersAccepted", adminService.managerMoreBannersAccepted());
		res.addObject("teachersOrderedByRatio", adminService.teachersOrderedByRatio());
		res.addObject("minAvgMaxClassesPerTeacher", adminService.minAvgMaxClassesPerTeacher());
		res.addObject("eventAverageDuration", adminService.eventAverageDuration());

		return res;
	}
}
