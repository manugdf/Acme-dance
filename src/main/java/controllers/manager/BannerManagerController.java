
package controllers.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Banner;
import domain.Manager;
import services.BannerService;
import services.FeeService;
import services.ManagerService;

@Controller
@RequestMapping("/banner/manager")
public class BannerManagerController extends AbstractController {

	@Autowired
	private ManagerService	managerService;

	@Autowired
	private BannerService	bannerService;

	@Autowired
	private FeeService		feeService;


	public BannerManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Assert.isTrue(this.managerService.LoggedIsManager());

		final ModelAndView res = new ModelAndView("banner/list");
		res.addObject("fee", this.feeService.selectFee());
		res.addObject("banners", this.bannerService.findAllByManager(this.managerService.findByPrincipal().getId()));
		res.addObject("requestURI", "banner/manager/list.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("banner/create");
		final Banner banner = this.bannerService.create();
		res.addObject("banner", banner);
		res.addObject("requestURI", "banner/manager/create.do");
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(Banner banner, final BindingResult binding) {

		ModelAndView res = new ModelAndView();

		banner = this.bannerService.reconstruct(banner, binding);
		if (binding.hasErrors()) {
			res = new ModelAndView("banner/create");
			res.addObject("requestURI", "banner/manager/create.do");
			res.addObject("banner", banner);

		} else
			try {
				this.bannerService.newBanner(banner);
				res = this.list();
			} catch (final Throwable oops) {
				res = new ModelAndView("banner/create");
				res.addObject("requestURI", "banner/manager/create.do");
				res.addObject("banner", banner);
				res.addObject("message", "banner.error.creditcard");
			}
		return res;

	}

	@RequestMapping(value = "/showMonthlyBill", method = RequestMethod.GET)
	public ModelAndView showMonthlyBill() {
		final ModelAndView res = new ModelAndView("manager/showMonthlyBill");
		final Manager manager = this.managerService.findByPrincipal();

		int numBannersAccepted = 0;
		for (final Banner b : manager.getBanner())
			if (b.getState().equals("ACCEPTED"))
				numBannersAccepted++;

		res.addObject("monthlyBill", this.managerService.getMonthTotalFee());
		res.addObject("numBanners", manager.getBanner().size());
		res.addObject("numBannersAccepted", numBannersAccepted);

		res.addObject("requestURI", "banner/manager/showMonthlyBill.do");

		return res;

	}

}
