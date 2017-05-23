
package controllers.administrator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.CensoredWords;
import services.CensoredWordsService;

@Controller
@RequestMapping("censoredWords/administrator")
public class CensoredWordsAdministratorController extends AbstractController {

	//Services----------------------------------------------------------------
	@Autowired
	private CensoredWordsService censoredWordsService;


	//Constructor------------------------------------------------------------

	public CensoredWordsAdministratorController() {
		super();
	}

	//Methods----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("censoredWords/list");

		final Collection<CensoredWords> censoredWords = this.censoredWordsService.findAll();

		result.addObject("censoredWords", censoredWords);
		result.addObject("requestUri", "censoredWords/administrator/list.do");

		return result;
	}

	protected ModelAndView createModelAndView(final CensoredWords censoredWords) {
		ModelAndView result;

		result = this.createModelAndView(censoredWords, null);

		return result;
	}

	protected ModelAndView createModelAndView(final CensoredWords censoredWords, final String message) {
		ModelAndView result;

		result = new ModelAndView("censoredWords/edit");
		result.addObject("requestURI", "censoredWords/administrator/create.do");
		result.addObject("censoredWords", censoredWords);
		result.addObject("message", message);
		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		final List<CensoredWords> censoredWords = new ArrayList<>(this.censoredWordsService.findAll());
		final CensoredWords censoredWord = censoredWords.get(0);
		result = this.createModelAndView(censoredWord);

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView addWord(@Valid final CensoredWords censoredWords, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createModelAndView(censoredWords);
		else
			try {
				this.censoredWordsService.save(censoredWords);

				result = new ModelAndView("redirect:/censoredWords/administrator/list.do");

			} catch (final Throwable oops) {
				oops.getStackTrace();
				result = this.createModelAndView(censoredWords, "cenwor.commit.error");
			}
		return result;
	}

}
