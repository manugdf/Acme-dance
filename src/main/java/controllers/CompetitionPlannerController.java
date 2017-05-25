
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import domain.CompetitionPlanner;
import forms.CompetitionPlannerForm;
import services.CompetitionPlannerService;

@Controller
@RequestMapping("competitionPlanner/")
public class CompetitionPlannerController extends AbstractController {

	@Autowired
	private CompetitionPlannerService competitionPlannerService;


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

		final CompetitionPlanner comp = this.competitionPlannerService.reconstruct(c);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res.addObject("requestUri", "competitionPlanner/register.do");
			res.addObject("competitionPlannerForm", c);

			res.addObject("edit", false);
		} else if (c.isAcceptTerms() != true) {
			res.addObject("competitionPlannerForm", c);
			res.addObject("requestUri", "competitionPlanner/create.do");
			res.addObject("message", "alumn.acceptTerms.error");

			res.addObject("edit", false);
		} else
			try {
				this.competitionPlannerService.save(comp);

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

		final CompetitionPlanner logged = this.competitionPlannerService.findByPrincipal();
		this.competitionPlannerService.reconstructEdit(logged, c);
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		System.out.println(encoder.encodePassword(c.getPassword(), null).equals(logged.getUserAccount().getPassword()));
		if (c.getPassword() == null || c.getPassword().length() == 0 || encoder.encodePassword(c.getPassword(), null).equals(logged.getUserAccount().getPassword()) == false) {

			res.addObject("competitionPlannerForm", c);
			res.addObject("requestUri", "competitionPlanner/edit.do");
			res.addObject("message", "cp.oldpassword.error");

			res.addObject("edit", true);

			return res;
		}
		if (!c.getNewPassword().equals(c.getRepeatNewPassword()) || c.getNewPassword().length() < 5 || c.getRepeatNewPassword().length() < 5 || c.getRepeatPassword().length() > 32 || c.getRepeatNewPassword().length() > 32) {

			res.addObject("competitionPlannerForm", c);
			res.addObject("requestUri", "competitionPlanner/edit.do");
			res.addObject("message", "cp.passwordmatch.error");

			res.addObject("edit", true);

			return res;
		}

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res.addObject("requestUri", "competitionPlanner/edit.do");
			res.addObject("competitionPlannerForm", c);
			res.addObject("edit", true);
		} else
			try {
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
				res.addObject("message", "alumn.commit.error");

				res.addObject("edit", true);
			}
		return res;
	}

}
