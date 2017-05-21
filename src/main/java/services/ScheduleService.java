
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Schedule;
import repositories.ScheduleRepository;

@Service
@Transactional
public class ScheduleService {

	@Autowired
	private ScheduleRepository	scheduleRepository;
	@Autowired
	private DanceClassService	danceClassService;


	// Constructor
	public ScheduleService() {
		super();
	}

	// Simple CRUD methods

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
}
