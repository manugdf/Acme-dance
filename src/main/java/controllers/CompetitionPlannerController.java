
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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


	@RequestMapping("create")
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("competitionPlanner/edit");

		final CompetitionPlannerForm c = new CompetitionPlannerForm();
		res.addObject("CompetitionPlannerForm", c);
		res.addObject("requestUri", "competitionPlanner/create.do");

		return res;

	}

	@RequestMapping(value = "create", params = "save")
	public ModelAndView create(@Valid final CompetitionPlannerForm c, final BindingResult binding) {
		final ModelAndView res = new ModelAndView("competitionPlanner/edit");

		final CompetitionPlanner comp = this.competitionPlannerService.reconstruct(c, binding);

		if (binding.hasErrors()) {
			System.out.println(binding.getAllErrors());
			res.addObject("CompetitionPlannerForm", c);
		} else
			try {
				this.competitionPlannerService.save(comp);

			} catch (final Exception e) {

				System.out.println(e);
				res.addObject("CompetitionPlannerForm", c);
			}
		return res;
	}
}
