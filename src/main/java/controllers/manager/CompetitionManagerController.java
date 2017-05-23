package controllers.manager;

import controllers.AbstractController;
import domain.Competition;
import domain.DanceSchool;
import forms.SelectDanceSchoolForm;
import org.hibernate.sql.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.CompetitionService;
import services.DanceSchoolService;
import services.ManagerService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.Date;

@Controller
@RequestMapping("/competition/manager")
public class CompetitionManagerController extends AbstractController {
    //Services
    @Autowired
    CompetitionService competitionService;

    @Autowired
    DanceSchoolService danceSchoolService;

    @Autowired
    ManagerService managerService;

    //Constructor
    public CompetitionManagerController(){super();}

    //Signup
    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup(@RequestParam final int competitionId) {
        ModelAndView res;
        SelectDanceSchoolForm selectDanceSchoolForm = new SelectDanceSchoolForm();
        res = signupModelAndView(selectDanceSchoolForm, competitionId);


        return res;
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST, params = "save")
    public ModelAndView signup(SelectDanceSchoolForm selectDanceSchoolForm, @RequestParam final int competitionId, final BindingResult bindingResult) {
        ModelAndView res;
        Competition competition = this.competitionService.findOne(competitionId);
        int oldSize = competition.getDanceSchools().size();
        competition = this.competitionService.signupDanceSchool(selectDanceSchoolForm, competitionId);

        if(bindingResult.hasErrors()){
            res = signupModelAndView(selectDanceSchoolForm, competitionId);
        }else{
            try{
                Assert.isTrue(competition.getLimitSchool()>=competition.getDanceSchools().size());
                Assert.isTrue(competition.getLimitInscription().after(new Date()));
                if(selectDanceSchoolForm.getDanceSchool()==null){
                    res = signupModelAndView(selectDanceSchoolForm, competitionId, "competition.select.empty");
                }else{
                    this.competitionService.save(competition);
                    res = new ModelAndView("redirect:/competition/list.do");
                }

            }catch (Throwable oops){
                res = signupModelAndView(selectDanceSchoolForm, competitionId, "competition.commit.error");
            }
        }
        return res;
    }

    public ModelAndView signupModelAndView(SelectDanceSchoolForm selectDanceSchoolForm, int competitionId){
        ModelAndView res = signupModelAndView(selectDanceSchoolForm,competitionId, null);

        return res;
    }

    public ModelAndView signupModelAndView(SelectDanceSchoolForm selectDanceSchoolForm,int competitionId, String message){
        ModelAndView res = new ModelAndView("competition/signup");
        Collection<DanceSchool> myDanceSchools = managerService.findByPrincipal().getDanceSchools();
        Competition competition = this.competitionService.findOne(competitionId);
        for(DanceSchool d:competition.getDanceSchools()){
            if(myDanceSchools.contains(d)){
                myDanceSchools.remove(d);
            }
        }
        res.addObject("requestUri", "competition/manager/signup.do?competitionId="+competitionId);
        res.addObject("selectDanceSchoolForm", selectDanceSchoolForm);
        res.addObject("myDanceSchools", myDanceSchools);
        res.addObject("message", message);

        return res;
    }

    }
