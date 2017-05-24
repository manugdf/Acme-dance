package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Banner;
import domain.Manager;
import services.ManagerService;

@Controller
@RequestMapping("/banner/manager")
public class BannerManagerController extends AbstractController{
	
	@Autowired
	private ManagerService managerService;
	
	public BannerManagerController() {
		super();
	}

	@RequestMapping(value = "/showMonthlyBill", method = RequestMethod.GET)
	public ModelAndView showMonthlyBill() {
		final ModelAndView res = new ModelAndView("manager/showMonthlyBill");
		Manager manager=managerService.findByPrincipal();
		
		int numBannersAccepted=0;
		for(Banner b : manager.getBanner()){
			if(b.getState().equals("ACCEPTED")){
				numBannersAccepted++;
			}
		}
		
		res.addObject("monthlyBill", managerService.getMonthTotalFee());
		res.addObject("numBanners", manager.getBanner().size());
		res.addObject("numBannersAccepted", numBannersAccepted);
		
		res.addObject("requestURI", "banner/manager/showMonthlyBill.do");

		return res;

	}

}
