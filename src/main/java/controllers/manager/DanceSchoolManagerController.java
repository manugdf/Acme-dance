
package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceSchool;
import domain.Manager;
import forms.DanceSchoolForm;
import forms.SearchForm;
import services.DanceSchoolService;
import services.ManagerService;

@Controller
@RequestMapping("/danceSchool/manager")
public class DanceSchoolManagerController extends AbstractController {

	@Autowired
	private DanceSchoolService	danceSchoolService;

	@Autowired
	private ManagerService		managerService;


	public DanceSchoolManagerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceSchool/listByManager");
		res.addObject("danceSchools", this.danceSchoolService.findAllByManager(this.managerService.findByPrincipal().getId()));
		res.addObject("requestURI", "danceSchool/manager/list.do");
		res.addObject("searchForm", new SearchForm());

		res.addObject("partnerview", false);
		res.addObject("isManager", true);
		return res;

	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		final ModelAndView res = new ModelAndView("danceSchool/create");

		final DanceSchoolForm danceSchoolForm = new DanceSchoolForm();

		res.addObject("requestUri", "danceSchool/manager/create.do");
		res.addObject("danceSchoolForm", danceSchoolForm);
		res.addObject("edit", false);
		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView create(@Valid final DanceSchoolForm danceSchoolForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("danceSchool/create");

		final DanceSchool danceSchool = this.danceSchoolService.reconstruct(danceSchoolForm, bindingResult);
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res.addObject("requestUri", "danceSchool/manager/create.do");
			res.addObject("edit", false);
			res.addObject("danceSchoolForm", danceSchoolForm);
		} else
			try {
				this.danceSchoolService.newDanceSchool(danceSchool);
				res = new ModelAndView("redirect:list.do");

			} catch (final DataIntegrityViolationException oops) {

				res = new ModelAndView("danceSchool/create");
				res.addObject("danceSchoolForm", danceSchoolForm);
				res.addObject("edit", false);
				res.addObject("message", "danceschool.error");

			}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {

		ModelAndView res = new ModelAndView("danceSchool/edit");
		try {
			final Manager logged = this.managerService.findByPrincipal();
			Assert.isTrue(this.danceSchoolService.findOne(id).getManager().getId() == logged.getId());
			Assert.isTrue(this.danceSchoolService.findOne(id).getState().equals("ACCEPTED"));

			DanceSchoolForm danceSchoolForm = new DanceSchoolForm();
			final DanceSchool danceSchool = this.danceSchoolService.findOne(id);
			danceSchoolForm = this.danceSchoolService.reconstructForm(danceSchool);

			res.addObject("requestUri", "danceSchool/manager/edit.do?id=" + id);
			res.addObject("danceSchoolForm", danceSchoolForm);
			res.addObject("edit", true);

		} catch (final Throwable e) {
			res = this.list();
			res.addObject("message", "danceschool.error.cant.edit");
		}

		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final DanceSchoolForm danceSchoolForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("danceSchool/edit");
		DanceSchool danceSchool = this.danceSchoolService.findOne(danceSchoolForm.getDanceSchoolId());

		try {

			danceSchool = this.danceSchoolService.reconstructEdit(danceSchoolForm, danceSchool);

			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors());
				res.addObject("requestUri", "danceSchool/manager/edit.do?id=" + danceSchool.getId());
				res.addObject("danceSchoolForm", danceSchoolForm);
				res.addObject("edit", true);
			} else {
				this.danceSchoolService.editDanceSchool(danceSchool);

				res = new ModelAndView("redirect:list.do");
			}
		} catch (final Throwable e) {

			System.out.println(e.getMessage());
			res.addObject("edit", true);
			res.addObject("message", "danceschool.error");

		}
		return res;

	}

}
