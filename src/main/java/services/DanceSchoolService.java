
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import domain.Award;
import domain.Competition;
import domain.DanceClass;
import domain.DanceSchool;
import domain.Event;
import domain.Manager;
import repositories.DanceSchoolRepository;

@Service
@Transactional
public class DanceSchoolService {

	@Autowired
	private DanceSchoolRepository	danceSchoolRepository;

	@Autowired
	private CompetitionService		competitionService;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private Validator				validator;


	// Constructor
	public DanceSchoolService() {
		super();
	}

	// Simple CRUD methods

	public DanceSchool create() {
		final DanceSchool res = new DanceSchool();
		res.setState("PENDING");
		res.setManager(this.managerService.findByPrincipal());
		res.setDanceClasses(new ArrayList<DanceClass>());
		res.setEvents(new ArrayList<Event>());
		res.setCompetitions(new ArrayList<Competition>());
		res.setAwards(new ArrayList<Award>());
		return res;
	}

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

	public DanceSchool newDanceSchool(DanceSchool danceSchool) {

		Assert.isTrue(this.managerService.LoggedIsManager());
		final Manager logged = this.managerService.findByPrincipal();
		Assert.isTrue(danceSchool.getManager().equals(logged));

		danceSchool = this.save(danceSchool);
		this.managerService.save(logged);

		return danceSchool;
	}

	public DanceSchool editDanceSchool(DanceSchool danceSchool) {
		final Manager logged = this.managerService.findByPrincipal();
		Assert.isTrue(danceSchool.getManager().equals(logged));
		danceSchool = this.save(danceSchool);
		return danceSchool;
	}

	public DanceSchool reconstruct(final DanceSchool danceSchool, final BindingResult binding) {
		DanceSchool res;

		if (danceSchool.getId() == 0)
			res = danceSchool;
		else {
			res = this.danceSchoolRepository.findOne(danceSchool.getId());
			res.setName(danceSchool.getName());
			res.setDescription(danceSchool.getDescription());
			res.setLocation(danceSchool.getLocation());
			res.setPhone(danceSchool.getPhone());
			res.setPicture(danceSchool.getPicture());
			res.setState(danceSchool.getState());

			this.validator.validate(res, binding);
		}
		return res;

	}

	public Collection<DanceSchool> findAllByManager(final int managerId) {
		return this.danceSchoolRepository.findAllByManager(managerId);
	}

	public Collection<DanceSchool> findSchoolsByCompetition(final int competitionId) {
		return this.danceSchoolRepository.findSchoolsByCompetition(competitionId);
	}

	public Collection<DanceSchool> findSchoolsByAlumn(final int alumnId) {
		return this.danceSchoolRepository.findSchoolsByAlumn(alumnId);
	}

}
