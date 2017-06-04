
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionPlannerService;
import domain.CompetitionPlanner;
import forms.CompetitionPlannerForm;

@Controller
@RequestMapping("competitionPlanner/")
public class CompetitionPlannerController extends AbstractController {

	@Autowired
	private CompetitionPlannerService	competitionPlannerService;


	@RequestMapping("register")
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("competitionPlanner/edit");

		final CompetitionPlannerForm c = new CompetitionPlannerForm();
		res.addObject("competitionPlannerForm", c);
		res.addObject("requestUri", "competitionPlanner/register.do");
		res.addObject("edit", false);
		return res;

	}

	@RequestMapping(value = "register", params = "save")
	public ModelAndView create(@Valid final CompetitionPlannerForm c, final BindingResult binding) {
		ModelAndView res = new ModelAndView("competitionPlanner/edit");

		if (c.getPassword().equals(c.getRepeatPassword())) {
			final CompetitionPlanner comp = this.competitionPlannerService.reconstruct(c);

			if (binding.hasErrors()) {
				System.out.println(binding.getAllErrors());
				res.addObject("requestUri", "competitionPlanner/register.do");
				res.addObject("competitionPlannerForm", c);

				res.addObject("edit", false);
			} else if (c.isAcceptTerms() != true) {
				res.addObject("competitionPlannerForm", c);
				res.addObject("requestUri", "competitionPlanner/register.do");
				res.addObject("message", "alumn.acceptTerms.error");

				res.addObject("edit", false);
			} else
				try {
					this.competitionPlannerService.register(comp);

					res = new ModelAndView("redirect:/welcome/index.do");

				} catch (final DataIntegrityViolationException e) {
					res.addObject("competitionPlannerForm", c);
					res.addObject("message", "alumn.error.exists");

					res.addObject("edit", false);
				}

				catch (final Exception e) {

					System.out.println(e);
					res.addObject("competitionPlannerForm", c);
					res.addObject("message", "alumn.commit.error");

					res.addObject("edit", false);
				}
		} else {
			res.addObject("requestUri", "competitionPlanner/register.do");
			res.addObject("competitionPlannerForm", c);
			res.addObject("message", "cp.passwordmatch.error");
			res.addObject("edit", false);
		}
		return res;
	}

	@RequestMapping("edit")
	public ModelAndView edit() {
		final ModelAndView res = new ModelAndView("competitionPlanner/edit");
		final CompetitionPlanner logged = this.competitionPlannerService.findByPrincipal();
		final CompetitionPlannerForm c = new CompetitionPlannerForm();
		this.competitionPlannerService.reconstructForm(logged, c);

		res.addObject("competitionPlannerForm", c);
		res.addObject("requestUri", "competitionPlanner/edit.do");
		res.addObject("edit", true);
		return res;

	}

	@RequestMapping(value = "edit", params = "save")
	public ModelAndView edit(@Valid final CompetitionPlannerForm c, final BindingResult binding) {
		ModelAndView res = new ModelAndView("competitionPlanner/edit");

		CompetitionPlanner logged = this.competitionPlannerService.findByPrincipal();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		System.out.println(encoder.encodePassword(c.getPassword(), null).equals(logged.getUserAccount().getPassword()));

		if (c.getPassword() == null || c.getPassword().length() == 0 || encoder.encodePassword(c.getPassword(), null).equals(logged.getUserAccount().getPassword()) == false) {

			res.addObject("competitionPlannerForm", c);
			res.addObject("requestUri", "competitionPlanner/edit.do");
			res.addObject("message", "cp.oldpassword.error");

			res.addObject("edit", true);

			return res;
		}
		//		if ((c.getNewPassword().length() == 0 || c.getRepeatNewPassword().length() == 0 || c.getNewPassword().equals(c.getRepeatNewPassword()) == false) && c.getNewPassword().length() <= 4 || c.getRepeatNewPassword().length() <= 4
		//			|| c.getNewPassword().length() >= 31 || c.getRepeatNewPassword().length() >= 31) {
		//
		//			res.addObject("competitionPlannerForm", c);
		//			res.addObject("requestUri", "competitionPlanner/edit.do");
		//			res.addObject("message", "cp.passwordmatch.error");
		//
		//			res.addObject("edit", true);
		//
		//			return res;
		//		}
		//
		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res.addObject("requestUri", "competitionPlanner/edit.do");
			res.addObject("competitionPlannerForm", c);
			res.addObject("edit", true);
		} else
			try {
				if (c.getNewPassword().length() > 0 || c.getRepeatNewPassword().length() > 0) {
					Assert.isTrue(c.getNewPassword().equals(c.getRepeatNewPassword()) == true);
					Assert.isTrue(c.getNewPassword().length() >= 5 && c.getRepeatNewPassword().length() >= 5);
					Assert.isTrue(c.getNewPassword().length() <= 32 && c.getRepeatNewPassword().length() >= 32);

				}
				logged = this.competitionPlannerService.reconstructEdit(logged, c);

				this.competitionPlannerService.save(logged);

				res = new ModelAndView("redirect:/welcome/index.do");

			} catch (final DataIntegrityViolationException e) {
				res.addObject("competitionPlannerForm", c);
				res.addObject("message", "alumn.error.exists");

				res.addObject("edit", true);
			}

			catch (final Exception e) {

				System.out.println(e);
				res.addObject("competitionPlannerForm", c);
				res.addObject("message", "cp.passwordmatch.error");

				res.addObject("edit", true);
			}
		return res;
	}

}
