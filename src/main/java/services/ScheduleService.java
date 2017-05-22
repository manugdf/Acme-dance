
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ScheduleRepository;
import domain.Schedule;

@Service
@Transactional
public class ScheduleService {

	@Autowired
	private ScheduleRepository	scheduleRepository;
	@Autowired
	private DanceClassService	danceClassService;
	@Autowired
	private Validator			validator;


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

		Schedule res = this.create();

		if (s.getId() == 0)
			res = s;
		else {

			res.setDayOfWeek(s.getDayOfWeek());
			res.setStartDate(s.getStartDate());
			res.setEndTime(s.getEndTime());
			res.setClassroom(s.getClassroom());

		}
		this.validator.validate(res, binding);

		return res;

	}

}
