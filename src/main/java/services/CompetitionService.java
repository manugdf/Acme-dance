package services;

import domain.Competition;
import domain.DanceSchool;
import forms.SelectDanceSchoolForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import repositories.CompetitionRepository;

import javax.transaction.Transactional;
import java.util.Collection;

@Service
@Transactional
public class CompetitionService {

    //Repository
    @Autowired
    private CompetitionRepository competitionRepository;

    //Services

    //Constructor
    public CompetitionService(){super();}

    //CRUD METHODS
    public Collection<Competition> findAll() {
        return this.competitionRepository.findAll();
    }

    public Competition findOne(final int id) {
        Competition result;
        result = this.competitionRepository.findOne(id);
        return result;
    }

    public Competition save(final Competition competition) {
        Assert.notNull(competition);

        return this.competitionRepository.save(competition);
    }

    public void delete(final Competition competition) {
        Assert.notNull(competition);
        this.competitionRepository.delete(competition);
    }

    public Competition signupDanceSchool(SelectDanceSchoolForm selectDanceSchoolForm, int competitionId){
        Competition competition = this.competitionRepository.findOne(competitionId);
        DanceSchool danceSchool = selectDanceSchoolForm.getDanceSchool();
        //if(danceSchool!=null){
            competition.getDanceSchools().add(danceSchool);
        //}
        return competition;
    }
}
