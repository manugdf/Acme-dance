package usecases;

import domain.Competition;
import domain.DanceClass;
import domain.DanceSchool;
import forms.SelectDanceSchoolForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;
import services.CompetitionService;
import services.DanceSchoolService;
import services.ManagerService;
import utilities.AbstractTest;

import javax.transaction.Transactional;
import java.util.Date;

@ContextConfiguration(locations = {
        "classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CU19 extends AbstractTest {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private DanceSchoolService danceSchoolService;

    @Autowired
    private CompetitionService competitionService;

    //CU19: Un usuario logueado como empresario accede a una vista donde puede ver la lista de competiciones
    // activas y presentar a una de sus escuelas a dicha competición.
    //RF: Un actor que esté logueado como empresario puede:
    //Ver la lista de competiciones activas y presentar a una de sus escuelas a dicha competición.

    @Test
    public void joinInCompetition() {
        final Object[][] testingData = {
                {
                        "admin", IllegalArgumentException.class, 114, 111
                }, {
                "manager1", null, 114, 111
        }, {
                "manager2",  IllegalArgumentException.class, 114, 111
        }, {
                "manager1",  IllegalArgumentException.class, 114, 112
        }, {
                "manager1",  IllegalArgumentException.class, 114, 110
        }
        };
        for (int i = 0; i < testingData.length; i++)
            this.joinInCompetition((String) testingData[i][0], (Class<?>) testingData[i][1], (int) testingData[i][2], (int) testingData[i][3]);
    }

    protected void joinInCompetition(final String username, final Class<?> expected, final int danceSchoolId, final int competitonId) {
        Class<?> caught;
        caught = null;
        try {
            this.authenticate(username);
            managerService.LoggedIsManager();
            DanceSchool danceSchool = danceSchoolService.findOne(danceSchoolId);
            Assert.isTrue(danceSchool.getManager().getUserAccount().getUsername().equals(username));

            SelectDanceSchoolForm selectDanceSchoolForm = new SelectDanceSchoolForm();
            selectDanceSchoolForm.setDanceSchool(danceSchool);

            Competition competition = competitionService.signupDanceSchool(selectDanceSchoolForm, competitonId);
            Assert.isTrue(competition.getDanceSchools().size()<=competition.getLimitSchool());
            Assert.isTrue(competition.getLimitInscription().after(new Date()));

            competitionService.save(competition);

            this.unauthenticate();
        } catch (final Throwable oops) {
            caught = oops.getClass();
        }
        this.checkExceptions(expected, caught);
    }

}
