package controllers.teacher;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Alumn;
import domain.DanceCertificate;
import domain.DanceClass;
import domain.DanceTest;
import services.AlumnService;
import services.DanceTestService;

@Controller
@RequestMapping("/alumn/teacher")
public class AlumnTeacherController extends AbstractController{
	
	@Autowired
	private AlumnService alumnService;
	@Autowired
	private DanceTestService danceTestService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int danceTestId) {
		ModelAndView res = new ModelAndView("alumn/list");
		DanceTest danceTest=danceTestService.findOne(danceTestId);
		Collection<Alumn> alumnsDanceCertificates=new ArrayList<Alumn>();
		Collection<Alumn> aux=new ArrayList<Alumn>();
		
		for(DanceCertificate d:danceTest.getDanceCertificates()){
			alumnsDanceCertificates.add(d.getAlumn());
		}
		
		for(Alumn a: danceTest.getAlumns()){
			if(!alumnsDanceCertificates.contains(a)){
				aux.add(a);
			}
		}
		if(aux.isEmpty()){
			res.addObject("message", "alumn.danceCertificate.empty");
			res.addObject("requestURI", "alumn/teacher/list.do?danceTestId="+danceTestId);
			res.addObject("alumns", aux);
			res.addObject("danceTestId", danceTestId);
		}
		else{
			res.addObject("alumns", aux);
			res.addObject("allAlumns", danceTest.getAlumns());
			res.addObject("requestURI", "alumn/teacher/list.do");
			res.addObject("danceTestId", danceTestId);
		}
		return res;
	}

}
