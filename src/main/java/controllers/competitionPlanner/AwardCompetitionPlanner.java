
package controllers.competitionPlanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AwardService;
import services.CompetitionPlannerService;
import services.CompetitionService;
import controllers.AbstractController;
import domain.Award;
import domain.Competition;

@Controller
@RequestMapping("competitionPlanner/award")
public class AwardCompetitionPlanner extends AbstractController {

	@Autowired
	private AwardService				awardService;

	@Autowired
	private CompetitionService			competitionService;

	@Autowired
	private CompetitionPlannerService	competitionPlannerService;


	@RequestMapping("/giveAward")
	public ModelAndView giveAward(@RequestParam final int competitionId) {
		final ModelAndView res = new ModelAndView("award/create");

		Assert.isTrue(this.competitionPlannerService.LoggedIsCompetitionPlanner());
		final Competition c = this.competitionService.findOne(competitionId);
		final Award award = this.awardService.create();
		award.setCompetition(c);

		res.addObject("award", award);
		res.addObject("requestUri", "competitionPlanner/award/giveAward.do");
		res.addObject("danceSchools", c.getDanceSchools());

		return res;

	}

	@RequestMapping(value = "/giveAward", params = "save")
	public ModelAndView giveAward(Award award, final BindingResult binding) {
		ModelAndView res = new ModelAndView("award/create");

		award = this.awardService.reconstruct(award, binding);

		if (binding.hasErrors()) {
			res.addObject("award", award);
			res.addObject("requestUri", "competitionPlanner/award/giveAward.do");
			res.addObject("danceSchools", award.getCompetition().getDanceSchools());

		} else
			try {
				award = this.awardService.save(award);
				res = new ModelAndView("redirect:/award/listByCompetition.do?competitionId=" + award.getCompetition().getId());
			} catch (final Exception e) {
				res.addObject("award", award);
				res.addObject("message", "alumn.commit.error");
			}

		return res;

	}
}
