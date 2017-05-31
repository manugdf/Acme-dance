package usecases;

import domain.DanceClass;
import domain.Payment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.ActorService;
import services.AlumnService;
import services.DanceClassService;
import services.PaymentService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import javax.validation.constraints.Null;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU27 extends AbstractTest {
    @Autowired
    private ActorService actorService;

    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private AlumnService alumnService;


    //CU27: Un usuario logueado como alumno accede a la vista de una escuela (usando el buscador o
    // la lista completa del sistema), ve las clases que se imparten y se apunta a una de ellas,
    // haciendo un pago mensual o anual de la misma, siempre y cuando tenga una tarjeta de crédito válida.
    //RF: Un actor que esté logueado como alumno puede:
    //Seleccionar una escuela, navegar por sus clases y apuntarse a una de esas clases,
    // tras comprobar el sistema que la tarjeta de crédito del alumno es válida.

    @Test
    public void joinInDanceClass() {
        final Object[][] testingData = {
                {
                        "admin", NullPointerException.class, 118
                }, {
                "alumn1", null, 118
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.joinInDanceClass((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
    }

    protected void joinInDanceClass(final String username, final Class<?> expected, final int danceClassId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);

            actorService.checkCreditCard(alumnService.findByPrincipal().getCreditCard());

            Payment aux= paymentService.create();
            aux.setPaymentType("YEARLY");

            DanceClass danceClass = danceClassService.findOne(danceClassId);
            Assert.isTrue(danceClass.getMaxAlumns()>danceClass.getPayments().size());

            Payment payment = paymentService.reconstruct(aux, danceClassId, null);
            paymentService.save(payment);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
