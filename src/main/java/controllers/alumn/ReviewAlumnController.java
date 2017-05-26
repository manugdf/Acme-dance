package controllers.alumn;

import controllers.AbstractController;
import controllers.ReviewController;
import domain.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.AlumnService;
import services.ReviewService;
import services.TeacherService;

@Controller
@RequestMapping("/review/alumn")
public class ReviewAlumnController extends AbstractController {
    //Services
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private AlumnService alumnService;

    //Constructor
    public ReviewAlumnController(){super();}

    //Create
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int teacherId) {
        ModelAndView res;

        Review review = reviewService.create();

        res = createModelAndView(review, teacherId);

        return res;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@RequestParam int teacherId, Review review, BindingResult bindingResult){
        ModelAndView res;

        Review review1 = reviewService.reconstruct(review, teacherId, bindingResult);

        if(bindingResult.hasErrors()){
            res = createModelAndView(review, teacherId);
        }else{
            try{
                Assert.isTrue(!teacherService.findTeachersByAlumnReview(review1.getAlumn().getId()).contains(teacherService.findOne(teacherId)));
                Assert.isTrue(teacherService.teachersCanReview(review1.getAlumn().getId()).contains(teacherService.findOne(teacherId)));
                reviewService.save(review1);

                res = new ModelAndView("review/list");
                res.addObject("reviews", reviewService.findReviewByTeacher(teacherId));
                res.addObject("teacher", teacherService.findOne(teacherId));
                res.addObject("teachersReview", teacherService.findTeachersByAlumnReview(alumnService.findByPrincipal().getId()));
                res.addObject("teachersCanReview", teacherService.teachersCanReview(alumnService.findByPrincipal().getId()));
                res.addObject("requestURI", "review/list.do?teacherId="+teacherId);
            }catch (Throwable oops){
                res = createModelAndView(review, teacherId, "review.commit.error");
            }
        }
        return res;
    }

    //Other methods
    public ModelAndView createModelAndView(Review review, int teacherId){
        ModelAndView res = createModelAndView(review, teacherId, null);

        return res;
    }

    public ModelAndView createModelAndView(Review review, int teacherId, String message){
        ModelAndView res = new ModelAndView("review/edit");
        res.addObject("review", review);
        res.addObject("teacherId", teacherId);
        res.addObject("message", message);
        res.addObject("requestUri", "review/alumn/create.do?teacherId="+teacherId);

        return res;
    }
}
