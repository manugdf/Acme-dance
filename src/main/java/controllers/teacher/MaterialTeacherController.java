
package controllers.teacher;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Material;
import domain.Teacher;
import services.MaterialService;
import services.TeacherService;

@Controller
@RequestMapping("/material/teacher")
public class MaterialTeacherController extends AbstractController {

	//Service-----------------------------------------------------
	@Autowired
	private MaterialService	materialService;

	@Autowired
	private TeacherService	teacherService;

	//Constructor-------------------------------------------------


	public MaterialTeacherController() {
		super();
	}

	//Methods-----------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		final ModelAndView res = new ModelAndView("material/list");

		final Teacher teacher = this.teacherService.findByPrincipal();
		final Collection<Material> materials = teacher.getMaterials();

		res.addObject("materials", materials);
		res.addObject("requestURI", "material/teacher/list.do");
		return res;

	}

}
