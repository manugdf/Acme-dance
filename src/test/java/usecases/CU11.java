package usecases;

import domain.DanceClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.DanceClassService;
import services.ManagerService;
import utilities.AbstractTest;

import javax.transaction.Transactional;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU11 extends AbstractTest {
    @Autowired
    private DanceClassService danceClassService;

    @Autowired
    private ManagerService managerService;

    //CU 11: Un usuario logueado como empresario accede a una vista donde ve las clases de las escuelas
    // que han sido enviadas por él y decide modificar una. Esto incluye modificar los datos de estilo de baile,
    // número máximo de alumnos, precio mensual y descripción.
    //RF: Un actor que esté logueado como empresario puede:
    //Gestionar las clases de una escuela que haya sido enviada por el empresario y aceptada por el administrador.
    // Esto incluye crear una nueva clase, modificar los datos de estilo de baile, número máximo de alumnos,
    // precio mensual y descripción, eliminar la clase bajo previa confirmación y asignar o
    // desasignar uno de los profesores creados por el empresario para impartir la clase.
    // Sólo se puede eliminar la clase en caso de que no tenga profesores ni alumnos asignados.


    @Test
    public void editDanceSchool() {
        final Object[][] testingData = {
                {
                        "admin", IllegalArgumentException.class, 118
                }, {
                "manager1", null, 118
        }, {
                "manager2",  IllegalArgumentException.class, 118
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.editDanceSchool((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2]);
    }

    protected void editDanceSchool(final String username, final Class<?> expected, final int danceClassId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);

            managerService.LoggedIsManager();
            DanceClass danceClass = danceClassService.findOne(danceClassId);
            Assert.isTrue(danceClass.getDanceSchool().getManager().getUserAccount().getUsername().equals(username));
            danceClass.setMaxAlumns(80);
            danceClassService.save(danceClass);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

}
