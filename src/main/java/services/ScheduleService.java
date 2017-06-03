
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ScheduleRepository;
import domain.Schedule;
import forms.ScheduleForm;

@Service
@Transactional
public class ScheduleService {

	@Autowired
	private ScheduleRepository	scheduleRepository;
	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private Validator			validator;
	@Autowired
	private ActorService		actorService;
	@Autowired
	private ManagerService		managerService;


	// Constructor
	public ScheduleService() {
		super();
	}

	// Simple CRUD methods

	public Schedule create() {
		final Schedule res = new Schedule();
		return res;
	}

	public Schedule save(final Schedule s) {
		return this.scheduleRepository.save(s);
	}

	public Schedule createSchedule(final Schedule s) {
		Assert.isTrue(this.actorService.isAuthenticated());
		Assert.isTrue(this.managerService.LoggedIsManager());
		Assert.isTrue(s.getDanceClass().getDanceSchool().getManager().equals(this.managerService.findByPrincipal()));

		return this.save(s);
	}
	public Collection<Schedule> findAll() {
		return this.scheduleRepository.findAll();
	}

	public Schedule findOne(final int id) {
		Schedule result;
		result = this.scheduleRepository.findOne(id);
		return result;
	}

	public Collection<Schedule> findScheduleByClass(final int classId) {

		return this.danceClassService.findOne(classId).getSchedules();
	}

	public Schedule reconstruct(final Schedule s, final BindingResult binding) {

		Schedule res;

	//	if (s.getId() == 0) {
			res = this.create();
			res.setDanceClass(s.getDanceClass());
		//} else
		//	res = this.findOne(s.getId());

		res.setDayOfWeek(s.getDayOfWeek());
		res.setStartDate(s.getStartDate());
		res.setEndTime(s.getEndTime());
		res.setClassroom(s.getClassroom());

		this.validator.validate(res, binding);

		return res;

	}
	public Schedule editReconstruct(Schedule s){
		Schedule res;
		res = this.findOne(s.getId());
		res.setDayOfWeek(s.getDayOfWeek());
		res.setStartDate(s.getStartDate());
		res.setEndTime(s.getEndTime());
		res.setClassroom(s.getClassroom());

		return res;
	}

	public ScheduleForm createBySchedule(final Schedule s) {

		final ScheduleForm res = new ScheduleForm();

		res.setClassroom(s.getClassroom());
		res.setDanceClass(s.getDanceClass());
		res.setDayOfWeek(s.getDayOfWeek());
		res.setEndTime(s.getEndTime());
		res.setScheduleId(s.getId());
		res.setStartDate(s.getStartDate());

		return res;

	}
}
