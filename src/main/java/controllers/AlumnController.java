
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Alumn;
import forms.AlumnForm;
import services.AlumnService;

@Controller
@RequestMapping("/alumn")
public class AlumnController extends AbstractController {

	//Services-----------------------------------------------

	@Autowired
	private AlumnService alumnService;

	//Constructor--------------------------------------------


	public AlumnController() {
		super();
	}

	//Methods------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register() {
		final ModelAndView res = new ModelAndView("alumn/register");

		final AlumnForm alumnForm = new AlumnForm();

		res.addObject("requestURI", "alumn/register.do");
		res.addObject("alumnForm", alumnForm);
		res.addObject("edit", false);
		return res;

	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView register(@Valid final AlumnForm alumnForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("alumn/register");

		if (alumnForm.getPassword().equals(alumnForm.getConfirmPassword())) {
			final Alumn alumn = this.alumnService.reconstruct(alumnForm);

			if (bindingResult.hasErrors()) {
				res.addObject("requestURI", "alumn/register.do");
				res.addObject("edit", false);
				res.addObject("alumnForm", alumnForm);
			} else if (alumnForm.isAcceptTerms() != true) {
				res.addObject("alumnForm", alumnForm);
				res.addObject("edit", false);
				res.addObject("requestURI", "alumn/register.do");
				res.addObject("message", "alumn.acceptTerms.error");
			} else
				try {

					this.alumnService.register(alumn);

					res = new ModelAndView("redirect:/welcome/index.do");

				} catch (final DataIntegrityViolationException oops) {

					res = new ModelAndView("alumn/register");
					res.addObject("alumnForm", alumnForm);
					res.addObject("edit", false);
					res.addObject("message", "alumn.error.exists");

				} catch (final Throwable e) {

					res.addObject("edit", false);
					res.addObject("message", "alumn.commit.error");

				}

		} else {
			res.addObject("alumnForm", alumnForm);
			res.addObject("edit", false);
			res.addObject("message", "alumn.password.error");
		}
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		final ModelAndView res = new ModelAndView("alumn/edit");
		AlumnForm a = new AlumnForm();
		final Alumn alumn = this.alumnService.findByPrincipal();

		a = this.alumnService.reconstructForm(alumn);

		res.addObject("requestUri", "alumn/edit.do");
		res.addObject("alumnForm", a);
		res.addObject("edit", true);
		return res;

	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid final AlumnForm alumnForm, final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("alumn/edit");
		Alumn alumn = this.alumnService.findOne(alumnForm.getAlumnId());
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		if (alumnForm.getPassword().isEmpty() == false) {
			System.out.println(alumnForm.getAlumnId());

			try {
				Assert.isTrue(encoder.encodePassword(alumnForm.getPassword(), null).equals(alumn.getUserAccount().getPassword()));
			} catch (final Throwable oops) {
				res.addObject("alumnForm", alumnForm);
				res.addObject("edit", true);

				res.addObject("message", "alumn.password.error2");
				return res;
			}
			try {
				if (alumnForm.getNewpassword().length() > 0 && alumnForm.getRepeatnewpassword().length() > 0 || alumnForm.getNewpassword().length() > 0 || alumnForm.getRepeatnewpassword().length() > 0)
					Assert.isTrue(alumnForm.getNewpassword().equals(alumnForm.getRepeatnewpassword()));
				alumn = this.alumnService.reconstructEdit(alumnForm, alumn);

				if (bindingResult.hasErrors()) {
					System.out.println(bindingResult.getAllErrors());
					res.addObject("requestUri", "alumn/edit.do");
					res.addObject("alumnForm", alumnForm);
					res.addObject("edit", true);
				} else {

					this.alumnService.modify(alumn);

					res = new ModelAndView("redirect:../welcome/index.do");
				}
			} catch (final DataIntegrityViolationException oops) {

				res = new ModelAndView("alumn/edit");
				res.addObject("alumnForm", alumnForm);
				res.addObject("edit", true);
				res.addObject("message", "alumn.error.exists");

			} catch (final Throwable e) {

				System.out.println(e.getMessage());
				res.addObject("edit", true);
				res.addObject("message", "alumn.password.new");

			}
		} else {
			res.addObject("alumnForm", alumnForm);
			res.addObject("edit", true);

			res.addObject("message", "alumn.password.error");
		}
		return res;

	}
}
