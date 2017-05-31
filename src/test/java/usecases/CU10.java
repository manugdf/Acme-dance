package usecases;

import domain.DanceClass;
import domain.DanceSchool;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.DanceClassService;
import services.DanceSchoolService;
import services.ManagerService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU10 extends AbstractTest {
    @Autowired
    private DanceSchoolService danceSchoolService;

    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private ManagerService managerService;

    //CU 10: Un usuario logueado como empresario accede a una vista donde se listan las clases
    // de una de sus escuelas y crea una clase de baile a dicha escuela.
    //RF: Un actor que esté logueado como empresario puede:
    //Gestionar las clases de una escuela que haya sido enviada por el empresario y aceptada por el administrador.
    // Esto incluye crear una nueva clase, modificar los datos de estilo de baile, número máximo de alumnos,
    // precio mensual y descripción, eliminar la clase bajo previa confirmación y asignar o
    // desasignar uno de los profesores creados por el empresario para impartir la clase.
    // Sólo se puede eliminar la clase en caso de que no tenga profesores ni alumnos asignados.

    @Test
    public void createDanceClass() {
        final Object[][] testingData = {
                {
                        null, IllegalArgumentException.class, 114
                }, {
                "manager1", null, 114
        }, {
                "manager2",  IllegalArgumentException.class, 114
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.createDanceClass((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
    }

    protected void createDanceClass(final String username, final Class<?> expected, final int danceSchoolId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);

            managerService.LoggedIsManager();
            DanceClass danceClass = danceClassService.create();

            danceClass.setDescription("description");
            danceClass.setYearlyPrice(100);
            danceClass.setMonthlyPrice(10);
            danceClass.setMaxAlumns(20);
            danceClass.setStyle("Salsa");
            DanceSchool danceSchool = danceSchoolService.findOne(danceSchoolId);
            danceClass.setDanceSchool(danceSchool);

            Assert.isTrue(danceSchool.getManager().getUserAccount().getUsername().equals(username));

            danceClassService.save(danceClass);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

}
