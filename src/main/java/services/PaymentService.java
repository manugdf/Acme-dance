package services;

import domain.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.PaymentRepository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Date;

@Service
@Transactional
public class PaymentService {
    //Repository
    @Autowired
    private PaymentRepository paymentRepository;

    //Services

    //Constructor
    public PaymentService(){super();}

    public Payment create(){
        Payment res = new Payment();

        res.setStartDate(new Date());

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
}
