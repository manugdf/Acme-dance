package controllers.alumn;

import controllers.AbstractController;
import domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import services.ActorService;
import services.AlumnService;
import services.DanceClassService;
import services.PaymentService;

@Controller
@RequestMapping("/payment/alumn")
public class PaymentAlumnController extends AbstractController {

    //Services
    @Autowired
    PaymentService paymentService;

    @Autowired
    AlumnService alumnService;

    @Autowired
    DanceClassService danceClassService;

    @Autowired
    ActorService actorService;

    //Constructor
    public PaymentAlumnController(){super();}

    //Join in
    @RequestMapping(value = "/joinin", method = RequestMethod.GET)
    public ModelAndView create(@RequestParam int danceClassId) {
        ModelAndView res;

        Payment payment = paymentService.create();

        res = joininModelAndView(payment, danceClassId);

        return res;
    }

    @RequestMapping(value = "/joinin", method = RequestMethod.POST, params = "save")
    public ModelAndView save(@RequestParam int danceClassId, Payment payment, BindingResult bindingResult){
        ModelAndView res;


        Payment payment1 = paymentService.reconstruct(payment, danceClassId, bindingResult);

        if(bindingResult.hasErrors()){
            if(payment.getPaymentType()=="MONTHLY" || payment.getPaymentType()=="YEARLY"){
                res = joininModelAndView(payment, danceClassId);
            }else{
                res = joininModelAndView(payment, danceClassId, "payment.select.error");
            }
        }else{
            try{
                if(!actorService.checkCreditCard(alumnService.findByPrincipal().getCreditCard())){
                    res = joininModelAndView(payment, danceClassId, "payment.creditCard.error");
                }else{
                    paymentService.save(payment1);
                    res = new ModelAndView("danceClass/list");
                    res.addObject("danceClasses", this.danceClassService.findDanceClassesBySchool(danceClassService.findOne(danceClassId).getDanceSchool().getId()));
                    res.addObject("requestURI", "danceClass/list.do");
                    res.addObject("danceschool", danceClassService.findOne(danceClassId).getDanceSchool());
                    res.addObject("danceSchoolId", danceClassService.findOne(danceClassId).getDanceSchool().getId());
                    res.addObject("myClasses", false);
                    res.addObject("danceClassesJoinIn", danceClassService.findDanceClassActiveByAlumn(alumnService.findByPrincipal().getId()));

                }

            }catch (Throwable oops){
                res = joininModelAndView(payment, danceClassId, "payment.commit.error");
            }
        }
        return res;
    }

    //Other methods

    public ModelAndView joininModelAndView(Payment payment, int danceClassId){
        ModelAndView res = joininModelAndView(payment, danceClassId, null);

        return res;
    }

    public ModelAndView joininModelAndView(Payment payment, int danceClassId, String message){
        ModelAndView res = new ModelAndView("payment/joinin");
        res.addObject("payment", payment);
        res.addObject("danceschool", danceClassService.findOne(danceClassId).getDanceSchool());
        res.addObject("message", message);
        res.addObject("requestUri", "payment/alumn/joinin.do?danceClassId="+danceClassId);

        return res;
    }
}
