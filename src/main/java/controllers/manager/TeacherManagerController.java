package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
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
	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("teacher/listManager");
		res.addObject("teachers", managerService.findByPrincipal().getTeachers());
		res.addObject("requestURI", "teacher/manager/list.do");
		res.addObject("managerTeacher", true);
		
		return res;

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
					
					res = list();

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
	
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int teacherId) {
		final ModelAndView res = new ModelAndView("teacher/edit");
		TeacherForm teacherForm = new TeacherForm();
		final Teacher teacher = this.teacherService.findOne(teacherId);

		teacherForm = this.teacherService.reconstructForm(teacher);

		res.addObject("requestUri", "teacher/manager/edit.do?teacherId="+teacherId);
		res.addObject("teacherForm", teacherForm);
		res.addObject("teacherId", teacherId);
		res.addObject("edit", true);
		return res;

	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@RequestParam final int teacherId,@Valid final TeacherForm teacherForm,final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("teacher/edit");
		Teacher teacher=teacherService.findOne(teacherId);
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (teacherForm.getPassword().isEmpty() == false) {
			try {
				Assert.isTrue(encoder.encodePassword(teacherForm.getPassword(), null).equals(teacher.getUserAccount().getPassword()));
			} catch (final Throwable oops) {
				res.addObject("teacherForm", teacherForm);
				res.addObject("edit", true);

				res.addObject("message", "teacher.password.error2");
				return res;
			}
			try {
				if (teacherForm.getNewpassword().length() > 0 && teacherForm.getRepeatnewpassword().length() > 0 || teacherForm.getNewpassword().length() > 0 || teacherForm.getRepeatnewpassword().length() > 0)
					Assert.isTrue(teacherForm.getNewpassword().equals(teacherForm.getRepeatnewpassword()));
				teacher = this.teacherService.reconstructEdit(teacherForm, teacher);

				if (bindingResult.hasErrors()) {
					System.out.println(bindingResult.getAllErrors());
					res.addObject("requestUri", "teacher/manager/edit.do");
					res.addObject("teacherForm", teacherForm);
					res.addObject("edit", true);
				} else {
					

					this.teacherService.modify(teacher);

					res = list();
					res.addObject("teachers", managerService.findByPrincipal().getTeachers());
				}
			} catch (final DataIntegrityViolationException oops) {

				res = new ModelAndView("teacher/edit");
				res.addObject("teacherForm", teacherForm);
				res.addObject("edit", true);
				res.addObject("message", "teacher.error.exists");

			} catch (final Throwable e) {

				System.out.println(e.getMessage());
				res.addObject("edit", true);
				res.addObject("message", "teacher.password.new");

			}
		} else {
			res.addObject("teacherForm", teacherForm);
			res.addObject("edit", true);

			res.addObject("message", "teacher.password.error");
		}
		return res;

	}
	

}
