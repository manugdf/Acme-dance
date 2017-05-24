
package controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import services.AdminService;
import services.BannerService;

@Controller
@RequestMapping("/banner/administrator")
public class BannerAdministratorController extends AbstractController {

	//Services
	@Autowired
	private BannerService	bannerService;

	@Autowired
	private AdminService	adminService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		this.adminService.checkLoggedIsAdmin();

		final ModelAndView res = new ModelAndView("banner/listAdmin");

		res.addObject("banners", this.bannerService.findAllPending());

		res.addObject("requestUri", "banner/administrator/list.do");
		return res;
	}

}
