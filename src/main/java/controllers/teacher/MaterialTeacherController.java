
package controllers.teacher;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.DanceClass;
import domain.Material;
import services.DanceClassService;
import services.MaterialService;
import services.TeacherService;

@Controller
@RequestMapping("/material/teacher")
public class MaterialTeacherController extends AbstractController {

	//Service-----------------------------------------------------
	@Autowired
	private MaterialService		materialService;

	@Autowired
	private TeacherService		teacherService;

	@Autowired
	private DanceClassService	danceClassService;

	//Constructor-------------------------------------------------


	public MaterialTeacherController() {
		super();
	}

	//Methods-----------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceClassId) {
		final ModelAndView res = new ModelAndView("material/list");

		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		final Collection<Material> materials = danceClass.getMaterials();

		res.addObject("materials", materials);
		res.addObject("danceClassId", danceClass.getId());
		res.addObject("requestURI", "material/teacher/list.do");
		return res;
	}

	protected ModelAndView createEditModelAndView(final Material material) {
		ModelAndView result;

		result = this.createEditModelAndView(material, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final Material material, final String message) {
		ModelAndView result;

		result = new ModelAndView("material/edit");
		result.addObject("material", material);
		result.addObject("message", message);
		result.addObject("requestURI", "material/teacher/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int danceClassId) {
		ModelAndView result;
		final Material material = this.materialService.create();
		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		material.setDanceClass(danceClass);

		Assert.notNull(material);

		result = this.createEditModelAndView(material);
		result.addObject("requestURI", "material/teacher/edit.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int id) {
		ModelAndView result;

		final Material material = this.materialService.findOne(id);

		Assert.notNull(material);

		result = this.createEditModelAndView(material);
		result.addObject("requestURI", "material/teacher/edit.do?=" + material.getId());
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Material material, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(material);
		else

			try {
				this.materialService.save(material);
				result = new ModelAndView("redirect:list.do?danceClassId=" + material.getDanceClass().getId());

			} catch (final Throwable oops) {
				result = this.createEditModelAndView(material, "material.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int id) {
		ModelAndView modelAndView;
		final Material material = this.materialService.findOne(id);

		try {
			this.materialService.delete(material);
			modelAndView = new ModelAndView("redirect:list.do?danceClassId=" + material.getDanceClass().getId());
		} catch (final Throwable oops) {
			modelAndView = new ModelAndView("redirect:list.do?danceClassId=" + material.getDanceClass().getId());
		}
		return modelAndView;

	}

}
