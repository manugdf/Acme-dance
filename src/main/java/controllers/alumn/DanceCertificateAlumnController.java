package controllers.alumn;

import controllers.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AlumnService;
import services.DanceCertificateService;

@Controller
@RequestMapping("/danceCertificate/alumn")
public class DanceCertificateAlumnController  extends AbstractController {
    //Services
    @Autowired
    private DanceCertificateService danceCertificateService;

    @Autowired
    private AlumnService alumnService;

    //Constructor
    public DanceCertificateAlumnController(){super();}

    //List
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list() {
        ModelAndView res = new ModelAndView("danceCertificate/list");
        res.addObject("danceCertificates", danceCertificateService.findDanceCertificatesByAlumn(alumnService.findByPrincipal().getId()));
        res.addObject("requestURI", "danceCertificate/alumn/list.do");
        return res;
    }
}
