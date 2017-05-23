package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import services.CompetitionService;

@Controller
@RequestMapping("/competition")
public class CompetitionController extends AbstractController {
    //Services
    @Autowired
    private CompetitionService competitionService;

    //Constructor
    public CompetitionController(){super();}

    //List
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(){
        ModelAndView res = new ModelAndView("competition/list");
        res.addObject("competitions", competitionService.findAll());
        res.addObject("requestURI", "competition/list.do");

        return res;
    }
}
