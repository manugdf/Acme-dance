package controllers.manager;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import domain.Manager;
import domain.Teacher;
import forms.TeacherForm;
import services.DanceClassService;
import services.ManagerService;
import services.TeacherService;

@Controller
@RequestMapping("/teacher/manager")
public class TeacherManagerController extends AbstractController{
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private ManagerService managerService;
	@Autowired
	private DanceClassService danceClassService;
	
	public TeacherManagerController() {
		super();
	}
	
	// Edition
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		final ModelAndView res = new ModelAndView("teacher/register");

		final TeacherForm teacherForm = new TeacherForm();

		res.addObject("requestUri", "teacher/manager/register.do");
		res.addObject("teacherForm", teacherForm);
		res.addObject("edit", false);
		return res;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final TeacherForm teacherForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("teacher/register");

		if (teacherForm.getPassword().equals(teacherForm.getConfirmPassword())) {
			final Teacher teacher = this.teacherService.reconstruct(teacherForm);

			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors());
				res.addObject("requestUri", "teacher/manager/register.do");
				res.addObject("edit", false);
				res.addObject("teacherForm", teacherForm);
			} else if (teacherForm.isAcceptTerms() != true) {
				res.addObject("teacherForm", teacherForm);
				res.addObject("edit", false);
				res.addObject("requestUri", "teacher/manager/register.do");
				res.addObject("message", "teacher.acceptTerms.error");
			} else
				try {
					
					this.teacherService.register(teacher);
					
					res = new ModelAndView("redirect:/welcome/index.do");

				} catch (final DataIntegrityViolationException oops) {

					res = new ModelAndView("teacher/register");
					res.addObject("teacherForm", teacherForm);
					res.addObject("edit", false);
					res.addObject("message", "teacher.error.exists");

				} catch (final Throwable e) {

					System.out.println(e.getMessage());
					res.addObject("edit", false);
					res.addObject("message", "teacher.commit.error");

				}

		} else {
			res.addObject("teacherForm", teacherForm);
			res.addObject("edit", false);
			res.addObject("message", "teacher.password.error");
		}
		return res;

	}
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int danceClassId) {
		ModelAndView res = new ModelAndView("teacher/add");
		Collection<Teacher> myTeachers=managerService.findByPrincipal().getTeachers();
		Collection<Teacher> danceClassTeachers=danceClassService.findOne(danceClassId).getTeachers();
		for(Teacher t:myTeachers){
			if(danceClassTeachers.contains(t)){
				myTeachers.remove(t);
			}
		}
			res.addObject("requestUri", "teacher/manager/add.do");
			res.addObject("myTeachers", myTeachers);
		
		return res;

	}
}
