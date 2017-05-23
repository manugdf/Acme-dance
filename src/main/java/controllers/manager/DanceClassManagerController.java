
package controllers.manager;

import java.util.ArrayList;
import java.util.Collection;

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

import services.DanceClassService;
import services.DanceSchoolService;
import services.ManagerService;
import controllers.AbstractController;
import domain.DanceClass;
import domain.DanceSchool;
import domain.Teacher;
import forms.DanceClassAuxForm;
import forms.DanceClassForm;

@Controller
@RequestMapping("/danceClass/manager")
public class DanceClassManagerController extends AbstractController {

	//Services
	@Autowired
	private DanceClassService	danceClassService;

	@Autowired
	private DanceSchoolService	danceSchoolService;

	@Autowired
	private ManagerService		managerService;


	//Constructor
	public DanceClassManagerController() {
		super();
	}

	//Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int id) {
		ModelAndView res;

		final DanceSchool danceSchool = this.danceSchoolService.findOne(id);
		System.out.println(danceSchool.getId());
		Assert.notNull(danceSchool);

		final DanceClassForm danceClassForm = new DanceClassForm();
		danceClassForm.setDanceSchool(danceSchool);

		res = this.createModelAndView(danceClassForm, id);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int id, final DanceClassForm danceClassForm, final BindingResult bindingResult) {
		ModelAndView res;
		final DanceClass danceClass = this.danceClassService.reconstruct(danceClassForm, bindingResult);
		if (bindingResult.hasErrors())
			res = this.createModelAndView(danceClassForm, id);
		else
			try {
				danceClass.setDanceSchool(this.danceSchoolService.findOne(id));
				this.danceClassService.save(danceClass);
				res = new ModelAndView("danceClass/list");
				res.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(id));
				res.addObject("requestURI", "danceClass/list.do");
				res.addObject("danceschool", this.danceSchoolService.findOne(id));
			} catch (final Throwable oops) {
				res = this.createModelAndView(danceClassForm, id, "danceClass.commit.error");
			}
		return res;
	}

	//Edit
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int danceClassId) {
		ModelAndView res;

		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		final DanceClassForm danceClassForm = new DanceClassForm();

		danceClassForm.setDanceSchool(danceClass.getDanceSchool());
		danceClassForm.setDescription(danceClass.getDescription());
		danceClassForm.setMaxAlumns(danceClass.getMaxAlumns());
		danceClassForm.setMonthlyPrice(danceClass.getMonthlyPrice());
		danceClassForm.setStyle(danceClass.getStyle());
		danceClassForm.setYearlyPrice(danceClass.getYearlyPrice());

		res = this.editModelAndView(danceClassForm, danceClassId);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@RequestParam final int danceClassId, final DanceClassForm danceClassForm, final BindingResult bindingResult) {
		ModelAndView modelAndView;

		danceClassForm.setDanceSchool(this.danceClassService.findOne(danceClassId).getDanceSchool());
		final DanceClass danceClass = this.danceClassService.reconstruct(danceClassForm, bindingResult);

		if (bindingResult.hasErrors())
			modelAndView = this.editModelAndView(danceClassForm, danceClassId);
		else
			try {
				final DanceClass res = this.danceClassService.reconstructEdit(danceClass, this.danceClassService.findOne(danceClassId));
				this.danceClassService.save(res);

				modelAndView = new ModelAndView("danceClass/list");
				modelAndView.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(res.getDanceSchool().getId()));
				modelAndView.addObject("requestURI", "danceClass/list.do");
				modelAndView.addObject("danceschool", this.danceSchoolService.findOne(res.getDanceSchool().getId()));
			} catch (final Throwable oops) {
				modelAndView = this.editModelAndView(danceClassForm, danceClassId, "danceClass.commit.error");
			}
		return modelAndView;
	}

	public ModelAndView createModelAndView(final DanceClassForm danceClassForm, final int id) {
		ModelAndView res;

		res = this.createModelAndView(danceClassForm, id, null);

		return res;
	}

	public ModelAndView createModelAndView(final DanceClassForm danceClassForm, final int id, final String message) {
		ModelAndView res;

		res = new ModelAndView("danceClass/edit");
		res.addObject("requestUri", "danceClass/manager/create.do?id=" + id);
		res.addObject("dance", this.danceSchoolService.findOne(id));
		res.addObject("danceClassForm", danceClassForm);
		res.addObject("message", message);

		return res;
	}

	public ModelAndView editModelAndView(final DanceClassForm danceClassForm, final int id) {
		ModelAndView res;

		res = this.editModelAndView(danceClassForm, id, null);

		return res;
	}

	public ModelAndView editModelAndView(final DanceClassForm danceClassForm, final int danceClassId, final String message) {
		ModelAndView res;

		res = new ModelAndView("danceClass/edit");
		res.addObject("requestUri", "danceClass/manager/edit.do?danceClassId=" + danceClassId);
		res.addObject("danceClassForm", danceClassForm);
		res.addObject("message", message);
		res.addObject("dance", this.danceClassService.findOne(danceClassId).getDanceSchool());

		return res;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int danceClassId,@RequestParam final int danceSchoolId) {
		ModelAndView res = new ModelAndView("danceClass/add");
		Collection<Teacher> myTeachers = this.managerService.findByPrincipal().getTeachers();
		Collection<Teacher> danceClassTeachers = this.danceClassService.findOne(danceClassId).getTeachers();
		Collection<Teacher> aux=new ArrayList<Teacher>();
		for(Teacher t:myTeachers){
			if(!danceClassTeachers.contains(t)){
				aux.add(t);
			}
		}
		
		DanceClassAuxForm danceClassAuxForm = new DanceClassAuxForm();
		danceClassAuxForm.setDanceClass(this.danceClassService.findOne(danceClassId));
		res.addObject("danceClassAuxForm", danceClassAuxForm);
		res.addObject("requestUri", "danceClass/manager/add.do?danceSchoolId="+danceSchoolId);
		res.addObject("myTeachers", aux);
		res.addObject("danceSchoolId", danceSchoolId);
		

		return res;

	}

	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "save")
	public ModelAndView add(@Valid final DanceClassAuxForm danceClassAuxForm, @RequestParam final int danceSchoolId, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("teacher/add");
		final DanceClass auxDanceClass = this.danceClassService.reconstructAux(danceClassAuxForm);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res.addObject("requestUri", "teacher/manager/add.do");
			res.addObject("danceClassAuxForm", danceClassAuxForm);
		} else
			try {

				this.danceClassService.save(auxDanceClass);

				res = new ModelAndView("redirect:/danceClass/list.do?danceSchoolId="+danceSchoolId);

			} catch (final DataIntegrityViolationException oops) {

				res = new ModelAndView("teacher/add");
				res.addObject("danceClassAuxForm", danceClassAuxForm);
				res.addObject("message", "teacher.error.exists");

			} catch (final Throwable e) {

				System.out.println(e.getMessage());
				res.addObject("message", "teacher.commit.error");

			}
		return res;
	}

	@RequestMapping("/list")
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("danceClass/list");
		final int id = this.managerService.findByPrincipal().getId();
		final Collection<DanceClass> ds = this.danceClassService.findAllByManager(id);

		res.addObject("danceClasses", ds);

		return res;
	}
}
