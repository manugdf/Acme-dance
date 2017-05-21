
package services;

import java.util.ArrayList;
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

	public Collection<DanceSchool> findAllAccepted() {
		return this.danceSchoolRepository.findAllAccepted();
	}

	public Collection<DanceSchool> findSchoolsByKeyword(final String word) {

		//return this.danceSchoolRepository.findSchoolByKeyword(word);
		final Collection<DanceSchool> res = new ArrayList<DanceSchool>();
		final Collection<DanceSchool> all = this.findAllAccepted();

		for (final DanceSchool d : all) {
			if (d.getDescription().toLowerCase().contains(word.toLowerCase()) && !res.contains(d))
				res.add(d);
			if (d.getName().toLowerCase().contains(word.toLowerCase()) && !res.contains(d))
				res.add(d);
			if (d.getLocation().getAddress().toLowerCase().contains(word.toLowerCase()) && !res.contains(d))
				res.add(d);
			if (d.getLocation().getCity().toLowerCase().contains(word.toLowerCase()) && !res.contains(d))
				res.add(d);
			if (d.getLocation().getProvince().toLowerCase().contains(word.toLowerCase()) && !res.contains(d))
				res.add(d);

		}
		return res;
	}
}
