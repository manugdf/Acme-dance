
package controllers.competitionPlanner;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import services.CompetitionPlannerService;
import services.CompetitionService;
import controllers.AbstractController;
import domain.Competition;
import domain.CompetitionPlanner;

@Controller
@RequestMapping("competition/competitionPlanner")
public class CompetitionCompetitionPlanner extends AbstractController {

	@Autowired
	private CompetitionService			competitionService;

	@Autowired
	private CompetitionPlannerService	competitionPlannerService;


	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("competition/myList");

		Assert.isTrue(this.competitionPlannerService.LoggedIsCompetitionPlanner());
		final CompetitionPlanner logged = this.competitionPlannerService.findByPrincipal();

		res.addObject("competitions", logged.getCompetitions());
		res.addObject("requestURI", "/competition/competitionPlanner/list.do");

		return res;
	}

	@RequestMapping("create")
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("competition/create");

		Assert.isTrue(this.competitionPlannerService.LoggedIsCompetitionPlanner());
		final Competition c = this.competitionService.create();

		res.addObject("competition", c);
		res.addObject("requestUri", "competition/competitionPlanner/create.do");

		return res;

	}

	@RequestMapping(value = "create", params = "save")
	public ModelAndView create(Competition competition, final BindingResult binding) {
		ModelAndView res = new ModelAndView("competition/create");

		final Date now = Calendar.getInstance().getTime();
		competition = this.competitionService.reconstruct(competition, binding);

		if (binding.hasErrors()) {
			res.addObject("competition", competition);
			res.addObject("requestUri", "competition/competitionPlanner/create.do");
		} else if (competition.getStartDate().after(now) && competition.getLimitInscription().after(competition.getStartDate()))
			try {
				this.competitionService.save(competition);
				res = new ModelAndView("redirect:/competition/competitionPlanner/list.do");

				res.addObject("message", "competition.created");

			} catch (final Exception e) {
				res.addObject("competition", competition);
				res.addObject("message", "alumn.commit.error");
			}
		else {
			res.addObject("competition", competition);
			res.addObject("message", "competition.dates.error");
		}
		return res;

	}

}
