
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;

import domain.Award;
import domain.Competition;
import domain.DanceClass;
import domain.DanceSchool;
import domain.Event;
import domain.Location;
import domain.Manager;
import forms.DanceSchoolForm;
import repositories.DanceSchoolRepository;

@Service
@Transactional
public class DanceSchoolService {

	@Autowired
	private DanceSchoolRepository	danceSchoolRepository;

	@Autowired
	private ManagerService			managerService;

	@Autowired
	private AdminService			adminService;


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

	public Collection<DanceSchool> findAllPending() {
		return this.danceSchoolRepository.findAllPending();
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
		danceSchool.setState("PENDING");

		danceSchool = this.save(danceSchool);
		this.managerService.save(logged);

		return danceSchool;
	}

	public DanceSchool editDanceSchool(final DanceSchool danceSchool) {
		final Manager logged = this.managerService.findByPrincipal();
		Assert.isTrue(danceSchool.getManager().getId() == logged.getId());
		return this.danceSchoolRepository.saveAndFlush(danceSchool);
	}

	public DanceSchoolForm reconstructForm(final DanceSchool danceSchool) {
		final DanceSchoolForm danceSchoolForm = new DanceSchoolForm();
		danceSchoolForm.setName(danceSchool.getName());
		danceSchoolForm.setDescription(danceSchool.getDescription());
		danceSchoolForm.setPhone(danceSchool.getPhone());
		danceSchoolForm.setPicture(danceSchool.getPicture());
		danceSchoolForm.setDanceSchoolId(danceSchool.getId());

		danceSchoolForm.setAddress(danceSchool.getLocation().getAddress());
		danceSchoolForm.setCity(danceSchool.getLocation().getCity());
		danceSchoolForm.setProvince(danceSchool.getLocation().getProvince());

		return danceSchoolForm;
	}

	public DanceSchool reconstruct(final DanceSchoolForm danceSchoolForm, final BindingResult binding) {
		final DanceSchool res = this.create();

		res.setName(danceSchoolForm.getName());
		res.setDescription(danceSchoolForm.getDescription());
		res.setPhone(danceSchoolForm.getPhone());
		res.setPicture(danceSchoolForm.getPicture());

		final Location location = new Location();
		location.setAddress(danceSchoolForm.getAddress());
		location.setCity(danceSchoolForm.getCity());
		location.setProvince(danceSchoolForm.getProvince());
		res.setLocation(location);
		return res;
	}

	public DanceSchool reconstructEdit(final DanceSchoolForm danceSchoolForm, final DanceSchool danceSchool) {

		danceSchool.setName(danceSchoolForm.getName());
		danceSchool.setDescription(danceSchoolForm.getDescription());
		danceSchool.setPhone(danceSchoolForm.getPhone());
		danceSchool.setPicture(danceSchoolForm.getPicture());

		final Location location = new Location();
		location.setAddress(danceSchoolForm.getAddress());
		location.setCity(danceSchoolForm.getCity());
		location.setProvince(danceSchoolForm.getProvince());
		danceSchool.setLocation(location);
		return danceSchool;

	}

	public DanceSchool rejectDanceSchool(final int id) {
		final boolean admin = this.adminService.isAdministrator();
		Assert.isTrue(admin == true);

		final DanceSchool danceSchool = this.findOne(id);
		danceSchool.setState("REJECTED");
		return this.save(danceSchool);
	}

	public DanceSchool acceptDanceSchool(final int id) {
		final boolean admin = this.adminService.isAdministrator();
		Assert.isTrue(admin == true);

		final DanceSchool danceSchool = this.findOne(id);
		danceSchool.setState("ACCEPTED");
		return this.save(danceSchool);
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
