package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.ManagerService;

@Controller
@RequestMapping("/manager/administrator")
public class ManagerAdministratorController extends AbstractController{

	
	@Autowired
	private ManagerService managerService;
	
	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView res = new ModelAndView("manager/list");

		res.addObject("managers", managerService.findAll());
		res.addObject("requestURI", "manager/administrator/list.do");
		return res;
	}
	
	
	
}
