package forms;


import domain.DanceClass;
import domain.DanceSchool;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SelectDanceSchoolForm {
    private DanceSchool danceSchool;

    @NotNull
    public DanceSchool getDanceSchool(){return danceSchool;}
    public void setDanceSchool(DanceSchool danceSchool){this.danceSchool=danceSchool;}
}
