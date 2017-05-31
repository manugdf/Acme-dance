package usecases;

import domain.Alumn;
import domain.DanceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.AlumnService;
import services.DanceTestService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.util.Date;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU31 extends AbstractTest {

    @Autowired
    DanceTestService danceTestService;

    @Autowired
    AlumnService alumnService;


    //CU31: Un usuario logueado como alumno accede a la vista de las clases a las que está apuntado,
    // accede a la vista de exámenes y decide apuntarse a uno de los que aún no han ocurrido.
    //RF: Un actor que esté logueado como alumno puede:
    //Ver los exámenes próximos de clases  y apuntarse a uno de esos exámenes.

    @Test
    public void joinInDanceTest() {
        final Object[][] testingData = {
                {
                        "admin", NullPointerException.class, 146
                }, {
                "alumn1", null, 146
        }, {
                "alumn2", IllegalArgumentException.class, 145
        }, {
                "alumn2", IllegalArgumentException.class, 146
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.joinInDanceTest((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
    }

    protected void joinInDanceTest(final String username, final Class<?> expected, final int danceTestId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);

            Alumn alumn = alumnService.findByPrincipal();
            DanceTest danceTest = danceTestService.findOne(danceTestId);

            Assert.isTrue(danceTest.getLimitInscription().after(new Date()));
            Assert.isTrue(!this.danceTestService.findDanceTestsByAlumn(alumn.getId()).contains(danceTest) && this.danceTestService.danceTestsCanJoinIn(alumn.getId()).contains(danceTest));

            danceTestService.joinInDanceTest(danceTestId);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
