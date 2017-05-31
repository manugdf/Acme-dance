package usecases;

import domain.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.ReviewService;
import services.TeacherService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU30 extends AbstractTest {
    @Autowired
    ReviewService reviewService;

    @Autowired
    TeacherService teacherService;


    //CU30: Un usuario logueado como alumno accede a la vista de las clases a las que está apuntado,
    // accede a la vista del profesor donde se puede acceder a la lista de opiniones que han escrito sobre él,
    // y decide escribir una opinión nueva.
    //RF: Un actor que esté logueado como alumno puede:
    //Acceder al perfil del profesor que imparte una de las clases a las que asiste y mandar una opinión sobre él.


    @Test
    public void reviewTeacher() {
        final Object[][] testingData = {
                {
                        "admin", NullPointerException.class, 108
                }, {
                "alumn2", null, 108
        }, {
                "alumn3", IllegalArgumentException.class, 108
        }, {
                "alumn1", IllegalArgumentException.class, 107
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.reviewTeacher((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
    }



    protected void reviewTeacher(final String username, final Class<?> expected, final int teacherId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);

            Review aux = reviewService.create();
            aux.setComment("Comment 1");
            aux.setScore(5);
            Review review = reviewService.reconstruct(aux, teacherId, null);

            Assert.isTrue(!teacherService.findTeachersByAlumnReview(review.getAlumn().getId()).contains(teacherService.findOne(teacherId)));
            Assert.isTrue(teacherService.teachersCanReview(review.getAlumn().getId()).contains(teacherService.findOne(teacherId)));

            reviewService.save(review);


            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }
}
