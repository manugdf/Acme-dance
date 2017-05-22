
package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
		ModelAndView res = new ModelAndView("teacher/register");

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
}
