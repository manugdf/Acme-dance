package services;

import domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import repositories.PaymentRepository;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class PaymentService {
    //Repository
    @Autowired
    private PaymentRepository paymentRepository;

    //Services
    @Autowired
    private AlumnService alumnService;

    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private Validator validator;

    //Constructor
    public PaymentService(){super();}

    public Payment create(){
        Payment res = new Payment();

        res.setStartDate(new Date());
        res.setAlumn(alumnService.findByPrincipal());

        return res;
    }

    //CRUD METHODS

    public Collection<Payment> findAll() {
        return this.paymentRepository.findAll();
    }

    public Payment findOne(final int id) {
        Payment result;
        result = this.paymentRepository.findOne(id);
        return result;
    }

    public Payment save(final Payment payment) {
        Assert.notNull(payment);

        return this.paymentRepository.save(payment);
    }

    public void delete(final Payment payment) {
        Assert.notNull(payment);
        this.paymentRepository.delete(payment);
    }

    //Other methods

    public Collection<Payment> paymentsActivesFromDanceClass(int danceClassId){
        return paymentRepository.paymentsActivesFromDanceClass(danceClassId);
    }

    public Payment reconstruct(Payment payment, int danceClassId, BindingResult bindingResult){
        Payment res = create();
        res.setDanceClass(danceClassService.findOne(danceClassId));
        res.setPaymentType(payment.getPaymentType());

        Calendar endDate = Calendar.getInstance();
        if(res.getPaymentType()=="MONTHLY"){
            endDate.add(Calendar.MONTH, 1);
            res.setEndDate(endDate.getTime());
        }else{
            endDate.add(Calendar.YEAR, 1);
            res.setEndDate(endDate.getTime());
        }

        validator.validate(res, bindingResult);

        return res;
    }
}
