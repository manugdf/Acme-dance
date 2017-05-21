
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.DanceClass;
import domain.DanceSchool;
import repositories.DanceClassRepository;

@Service
@Transactional
public class DanceClassService {

	@Autowired
	private DanceClassRepository	danceClassRepository;
	@Autowired
	private DanceSchoolService		danceSchoolService;


	// Constructor
	public DanceClassService() {
		super();
	}

	// Simple CRUD methods

	public Collection<DanceClass> findAll() {
		return this.danceClassRepository.findAll();
	}

	public DanceClass findOne(final int id) {
		DanceClass result;
		result = this.danceClassRepository.findOne(id);
		return result;
	}

	public Collection<DanceClass> findDanceClassesBySchool(final int danceSchoolId) {
		final DanceSchool school = this.danceSchoolService.findOne(danceSchoolId);
		final Collection<DanceClass> classes = school.getDanceClasses();
		return classes;
	}
}
