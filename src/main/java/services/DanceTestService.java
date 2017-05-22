
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.DanceTest;
import repositories.DanceTestRepository;

@Service
@Transactional
public class DanceTestService {

	@Autowired
	private DanceTestRepository danceTestRepository;


	// Constructor
	public DanceTestService() {
		super();
	}

	// Simple CRUD methods

	public Collection<DanceTest> findAll() {
		return this.danceTestRepository.findAll();
	}

	public DanceTest findOne(final int id) {
		DanceTest result;
		result = this.danceTestRepository.findOne(id);
		return result;
	}

}
