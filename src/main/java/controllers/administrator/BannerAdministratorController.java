
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
import domain.Banner;
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

	@RequestMapping(value = "/reject", method = RequestMethod.GET)
	public ModelAndView reject(@RequestParam final int id) {

		this.bannerService.rejectBanner(id);
		final ModelAndView res = new ModelAndView("redirect:list.do");

		return res;

	}

	@RequestMapping(value = "/accept", method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam final int id) {

		this.bannerService.acceptBanner(id);
		final ModelAndView res = new ModelAndView("redirect:list.do");

		return res;
	}

	// Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		final ModelAndView res = new ModelAndView("banner/edit");

		final Banner banner = this.bannerService.findOne(id);
		Assert.notNull(banner);

		res.addObject("requestUri", "banner/administrator/edit.do?id=" + id);
		res.addObject("banner", banner);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(final Banner banner, final BindingResult bindingResult) {
		ModelAndView res;

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res = new ModelAndView("banner/edit");
			res.addObject("banner", banner);
		} else
			try {
				final Banner bannerAux = this.bannerService.reconstructEdit(banner, bindingResult);
				res = this.list();

				this.bannerService.save(bannerAux);

			} catch (final Throwable oops) {
				res = new ModelAndView("banner/edit");
				res.addObject("banner", banner);
			}
		return res;
	}

}
