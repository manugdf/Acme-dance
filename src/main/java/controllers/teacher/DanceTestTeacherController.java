
package controllers.teacher;

import java.util.Collection;
import java.util.Date;

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
import domain.DanceTest;
import services.DanceClassService;
import services.DanceTestService;

@Controller
@RequestMapping("/danceTest/teacher")
public class DanceTestTeacherController extends AbstractController {

	@Autowired
	private DanceTestService	danceTestService;
	@Autowired
	private DanceClassService	danceClassService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceClassId) {
		final ModelAndView res = new ModelAndView("danceTest/list");
		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		final Collection<DanceTest> danceTests=danceClass.getDanceTests();
		
		res.addObject("danceTests", danceTests);
		res.addObject("danceClassId", danceClass.getId());
		res.addObject("requestURI", "danceTest/teacher/list.do");
		return res;

	}

	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int danceClassId) {
		ModelAndView res;

		final DanceTest danceTest=danceTestService.create();
		final DanceClass danceClass = this.danceClassService.findOne(danceClassId);
		danceTest.setDanceClass(danceClass);
		Assert.notNull(danceTest);

		res = this.createEditModelAndView(danceTest, danceClassId);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(DanceTest danceTest, final BindingResult bindingResult) {
		ModelAndView res;

		danceTest = this.danceTestService.reconstruct(danceTest, bindingResult);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res = this.createEditModelAndView(danceTest, danceTest.getDanceClass().getId());
		}else if(danceTest.getTestDate().before(danceTest.getLimitInscription())){
			res = this.createEditModelAndView(danceTest, danceTest.getDanceClass().getId(),"danceTest.errorOrden");
		}
		else if(danceTest.getLimitInscription().before(new Date())||danceTest.getTestDate().before(new Date())){
			res = this.createEditModelAndView(danceTest, danceTest.getDanceClass().getId(),"danceTest.errorFechaActual");
		}
		else
			try {
				danceTestService.createDanceTest(danceTest,danceTest.getDanceClass().getId());

				res = new ModelAndView("redirect:list.do?danceClassId=" + danceTest.getDanceClass().getId());
				res.addObject("danceClassId", danceTest.getDanceClass().getId());
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(danceTest,danceTest.getDanceClass().getId(),"danceTest.error");
			}
		return res;
	}
	
	protected ModelAndView createEditModelAndView(final DanceTest danceTest, final int danceClassId) {
		ModelAndView result;

		result = this.createEditModelAndView(danceTest, danceClassId,null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final DanceTest danceTest, final int danceClassId, final String message) {
		ModelAndView result;

		result = new ModelAndView("danceTest/create");
		result.addObject("danceTest", danceTest);
		result.addObject("danceClassId",danceClassId);
		result.addObject("message", message);
		result.addObject("requestURI", "danceTest/teacher/create.do");
		

		return result;
	}

}
