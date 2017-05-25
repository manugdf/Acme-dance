package controllers.teacher;

import controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Alumn;
import domain.DanceCertificate;
import domain.DanceClass;
import domain.DanceTest;
import services.AlumnService;
import services.DanceCertificateService;
import services.DanceClassService;
import services.TeacherService;

@Controller
@RequestMapping("/danceCertificate/teacher")
public class DanceCertificateTeacherController extends AbstractController{

	@Autowired
	private DanceCertificateService danceCertificateService;
	@Autowired
	private AlumnService alumnService;
	@Autowired
	private TeacherService teacherService;
	
	// List
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int alumnId) {
		final ModelAndView res = new ModelAndView("danceCertificate/list");
		final Alumn alumn=alumnService.findOne(alumnId);

		res.addObject("danceCertificates", alumn.getDanceCertificates());
		return res;

	}
	
	// Create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int alumnId, @RequestParam final int danceTestId) {
		ModelAndView res;

		final Alumn alumn = alumnService.findOne(alumnId);
		Assert.notNull(alumn);

		res = new ModelAndView("danceCertificate/create");
		res.addObject("danceCertificate", new DanceCertificate());
		res.addObject("danceTestId", danceTestId);

		return res;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@RequestParam final int alumnId, @RequestParam final int danceTestId, DanceCertificate danceCertificate, final BindingResult bindingResult) {
		ModelAndView res;

		danceCertificate = danceCertificateService.reconstruct(danceCertificate, alumnId, bindingResult);

		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getAllErrors());
			res= new ModelAndView("danceCertificate/create");
			res.addObject("danceCertificate", danceCertificate);
			res.addObject("alumnId", alumnId);
			res.addObject("danceTestId", danceTestId);
		} else {
			try {
				res= new ModelAndView("redirect:/danceCertificate/teacher/list.do?alumnId="+alumnId);
				DanceCertificate aux = danceCertificateService.save(danceCertificate);

				Alumn alumn=alumnService.findOne(alumnId);
				alumn.getDanceCertificates().add(aux);
				alumnService.save(alumn);


				res.addObject("danceCertificates", alumn.getDanceCertificates());
				
			} catch (final Throwable oops) {
				res= new ModelAndView("danceCertificate/create");
				res.addObject("danceCertificate", danceCertificate);
				res.addObject("danceTestId", danceTestId);
			}
		}
		return res;
	}
}
