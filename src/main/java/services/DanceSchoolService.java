
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.DanceSchool;
import repositories.DanceSchoolRepository;

@Service
@Transactional
public class DanceSchoolService {

	@Autowired
	private DanceSchoolRepository danceSchoolRepository;


	// Constructor
	public DanceSchoolService() {
		super();
	}

	// Simple CRUD methods

	public Collection<DanceSchool> findAll() {
		return this.danceSchoolRepository.findAll();
	}

	public DanceSchool findOne(final int id) {
		DanceSchool result;
		result = this.danceSchoolRepository.findOne(id);
		return result;
	}

	public DanceSchool save(final DanceSchool danceSchool) {

		return this.danceSchoolRepository.save(danceSchool);
	}
}
