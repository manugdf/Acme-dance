/*
 * WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.Banner;
import services.ActorService;
import services.BannerService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private BannerService	bannerService;

	@Autowired
	private ActorService	actorService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "welcome.greeting.stranger") String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		Banner banner = new Banner();
		final ArrayList<Banner> banners = (ArrayList<Banner>) this.bannerService.findAllAccepted();

		final ArrayList<Banner> validBanners = new ArrayList<Banner>();
		for (final Banner b : banners)
			if (this.actorService.checkCreditCard(b.getManager().getCreditCard()) == true)
				validBanners.add(b);

		final int valorEntero = (int) Math.floor(Math.random() * (validBanners.size()));
		banner = validBanners.get(valorEntero);
		final String url = banner.getUrl();

		final Actor loged = this.actorService.getLoggedActor();
		if (loged != null)
			name = loged.getName();
		else
			name = "welcome.greeting.stranger";
		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("banner", banner);
		result.addObject("moment", moment);
		result.addObject("url", url);

		return result;
	}

	@RequestMapping(value = "/terms", method = RequestMethod.GET)
	public ModelAndView terms() {
		final ModelAndView res = new ModelAndView("terms/terms");
		return res;
	}

	@RequestMapping(value = "/privacy", method = RequestMethod.GET)
	public ModelAndView privacy() {
		final ModelAndView res = new ModelAndView("privacy/privacy");
		return res;
	}
}
