package forms;

import domain.DanceClass;
import domain.Teacher;

public class DanceClassAuxForm {
	
	private Teacher teacher;
	private DanceClass danceClass;
	
	public Teacher getTeacher() {
		return this.teacher;
	}
	public void setTeacher(final Teacher teacher) {
		this.teacher=teacher;
	}
	
	public DanceClass getDanceClass(){
		return this.danceClass;
	}
	public void setDanceClass(final DanceClass danceClass){
		this.danceClass=danceClass;
	}

}
