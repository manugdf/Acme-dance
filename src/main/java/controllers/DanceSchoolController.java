
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.DanceSchool;
import forms.SearchForm;
import services.DanceSchoolService;

@Controller
@RequestMapping("/danceSchool")
public class DanceSchoolController extends AbstractController {

	@Autowired
	private DanceSchoolService danceSchoolService;


	public DanceSchoolController() {
		super();
	}

	@RequestMapping(value = "/listAll", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/list");
		res.addObject("danceSchools", this.danceSchoolService.findAllAccepted());
		res.addObject("requestURI", "danceSchool/listAll.do");
		res.addObject("searchForm", new SearchForm());

		res.addObject("partnerview", false);
		res.addObject("isManager", false);
		return res;

	}

	@RequestMapping(value = "/listByCompetition", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int competitionId) {
		final ModelAndView res = new ModelAndView("danceSchool/list");
		res.addObject("danceSchools", this.danceSchoolService.findSchoolsByCompetition(competitionId));
		res.addObject("requestURI", "danceSchool/listByCompetition.do");
		res.addObject("searchForm", new SearchForm());

		res.addObject("partnerview", false);
		res.addObject("isManager", false);

		return res;
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST, params = "search")
	public ModelAndView save(@Valid final SearchForm searchForm, final BindingResult binding) {

		final ModelAndView res = new ModelAndView("danceSchool/list");

		if (binding.hasErrors()) {
			res.addObject("danceSchools", this.danceSchoolService.findAllAccepted());
			res.addObject("requestURI", "danceSchool/listAll.do");
			res.addObject("searchForm", new SearchForm());
			System.out.println(binding.getAllErrors());
		} else
			try {
				final Collection<DanceSchool> schools = this.danceSchoolService.findSchoolsByKeyword(searchForm.getWord());
				res.addObject("danceSchools", schools);
				res.addObject("requestURI", "danceSchool/listAll.do");
				res.addObject("searchForm", new SearchForm());
			} catch (final Throwable oops) {
				res.addObject("danceSchools", this.danceSchoolService.findAllAccepted());
				res.addObject("requestURI", "danceSchool/listAll.do");
				res.addObject("searchForm", new SearchForm());
				System.out.println(oops.getMessage());
			}
		return res;
	}

}
