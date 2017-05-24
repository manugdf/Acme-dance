package controllers;

import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ReviewService;
import services.TeacherService;

@Controller
@RequestMapping("/review")
public class ReviewController  extends AbstractController {
    //Services
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TeacherService teacherService;

    //Contructor
    public ReviewController(){super();}

    //List
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView list(@RequestParam final int teacherId){
        ModelAndView res = new ModelAndView("review/list");
        res.addObject("reviews", reviewService.findReviewByTeacher(teacherId));
        res.addObject("teacher", teacherService.findOne(teacherId));
        res.addObject("requestURI", "review/list.do?teacherId="+teacherId);

        return res;
    }
}
