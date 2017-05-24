
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
		} else if (c.isAcceptTerms() != true) {
			res.addObject("competitionPlannerForm", c);
			res.addObject("requestUri", "competitionPlanner/create.do");
			res.addObject("message", "alumn.acceptTerms.error");
		} else
			try {
				this.competitionPlannerService.save(comp);

				res = new ModelAndView("redirect:/welcome/index.do");

			} catch (final DataIntegrityViolationException e) {
				res.addObject("competitionPlannerForm", c);
				res.addObject("message", "alumn.error.exists");
			}

			catch (final Exception e) {

				System.out.println(e);
				res.addObject("competitionPlannerForm", c);
				res.addObject("message", "alumn.commit.error");
			}
		return res;
	}
}
