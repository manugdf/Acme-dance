package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Manager;
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
	
	// Edit
	@RequestMapping(value = "/editFee", method = RequestMethod.GET)
	public ModelAndView editFee(@RequestParam final int managerId) {
		final ModelAndView res = new ModelAndView("manager/editFee");
		
		Manager manager=managerService.findOne(managerId);
		Assert.notNull(manager);

		res.addObject("requestUri", "manager/administrator/editFee.do?managerId="+managerId);
		res.addObject("manager", manager);
		res.addObject("managerId", managerId);
		res.addObject("edit", true);
		res.addObject("editFee", true);
		return res;

	}
	
	@RequestMapping(value = "/editFee", method = RequestMethod.POST, params = "save")
	public ModelAndView editFee(@RequestParam final int managerId, Manager manager, final BindingResult bindingResult) {
		ModelAndView res;
		
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res= new ModelAndView("manager/editFee");
			res.addObject("manager",manager);
			res.addObject("edit", true);
			res.addObject("editFee", true);
		} else {
			try {
				final Manager managerAux = this.managerService.reconstructEditFee(manager, managerId,bindingResult);
				res= list();
				
				managerService.save(managerAux);

				res.addObject("managers", managerService.findAll());
				
			} catch (final Throwable oops) {
				res= new ModelAndView("manager/editFee");
				res.addObject("manager",manager);
				res.addObject("edit", true);
				res.addObject("editFee", true);
			}
		}
		return res;
	}
	
	
}
