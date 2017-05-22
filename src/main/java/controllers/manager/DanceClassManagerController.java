package controllers.manager;

import controllers.AbstractController;
import domain.DanceClass;
import domain.DanceSchool;
import domain.Teacher;
import forms.DanceClassForm;
import forms.SearchForm;
import forms.DanceClassAuxForm;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.DanceClassService;
import services.DanceSchoolService;
import services.ManagerService;

@Controller
@RequestMapping("/danceClass/manager")
public class DanceClassManagerController  extends AbstractController {
    //Services
    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private DanceSchoolService danceSchoolService;

     @Autowired
     private ManagerService managerService;

    //Constructor
    public DanceClassManagerController(){super();}


    //Create
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam final int id) {
        ModelAndView res;

        DanceSchool danceSchool = danceSchoolService.findOne(id);
        System.out.println(danceSchool.getId());
        Assert.notNull(danceSchool);

        DanceClassForm danceClassForm = new DanceClassForm();
        danceClassForm.setDanceSchool(danceSchool);

        res = createEditModelAndView(danceClassForm, id);

        return res;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@RequestParam final int id, final DanceClassForm danceClassForm, final BindingResult bindingResult){
        ModelAndView res;
        DanceClass danceClass = danceClassService.reconstruct(danceClassForm, bindingResult);
        if(bindingResult.hasErrors()){
            res = this.createEditModelAndView(danceClassForm, id);
            System.out.println(bindingResult.getAllErrors());
        }else{
            try{
                danceClass.setDanceSchool(danceSchoolService.findOne(id));
                danceClassService.save(danceClass);
                res = new ModelAndView("danceClass/list");
                res.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(id));
                res.addObject("requestURI", "danceClass/list.do");
                res.addObject("danceschool", this.danceSchoolService.findOne(id));
            }catch (Throwable oops){
                res = this.createEditModelAndView(danceClassForm, id, "danceClass.commit.error");
            }
        }
        return res;
    }

    public ModelAndView createEditModelAndView(DanceClassForm danceClassForm, int id){
        ModelAndView res;

        res = createEditModelAndView(danceClassForm,id, null);

        return res;
    }

    public ModelAndView createEditModelAndView(DanceClassForm danceClassForm,int id, String message){
        ModelAndView res;

        res = new ModelAndView("danceClass/edit");
        res.addObject("requestUri", "danceClass/manager/create.do?id="+id);
        res.addObject("dance", this.danceSchoolService.findOne(id));
        res.addObject("danceClassForm", danceClassForm);
        res.addObject("message", message);

        return res;
    }
    
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView add(@RequestParam final int danceClassId) {
		ModelAndView res = new ModelAndView("danceClass/add");
		Collection<Teacher> myTeachers=managerService.findByPrincipal().getTeachers();
		Collection<Teacher> danceClassTeachers=danceClassService.findOne(danceClassId).getTeachers();
		for(Teacher t:myTeachers){
			if(danceClassTeachers.contains(t)){
				myTeachers.remove(t);
			}
		}
		final DanceClassAuxForm danceClassAuxForm = new DanceClassAuxForm();
		danceClassAuxForm.setDanceClass(danceClassService.findOne(danceClassId));
			res.addObject("danceClassAuxForm", danceClassAuxForm);
			res.addObject("requestUri", "danceClass/manager/add.do");
			res.addObject("myTeachers", myTeachers);
	
		
		return res;

	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, params = "save")
	public ModelAndView add(@Valid final DanceClassAuxForm danceClassAuxForm,final BindingResult bindingResult) {
		ModelAndView res = new ModelAndView("teacher/add");	
			DanceClass auxDanceClass=danceClassService.reconstructAux(danceClassAuxForm);

			if (bindingResult.hasErrors()) {
				System.out.println(bindingResult.getAllErrors());
				res.addObject("requestUri", "teacher/manager/add.do");
				res.addObject("danceClassAuxForm", danceClassAuxForm);
			}else
				try {
					
					this.danceClassService.save(auxDanceClass);
					
					res = new ModelAndView("redirect:/welcome/index.do");

				} catch (final DataIntegrityViolationException oops) {

					res = new ModelAndView("teacher/add");
					res.addObject("danceClassAuxForm", danceClassAuxForm);
					res.addObject("message", "teacher.error.exists");

				} catch (final Throwable e) {

					System.out.println(e.getMessage());
					res.addObject("message", "teacher.commit.error");

		} 
		return res;
	}
}
