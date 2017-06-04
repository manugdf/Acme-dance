
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.EventRepository;
import domain.Alumn;
import domain.Event;

@Service
@Transactional
public class EventService {

	@Autowired
	private EventRepository	eventRepository;

	@Autowired
	private Validator		validator;

	@Autowired
	private ManagerService	managerService;


	// Constructor
	public EventService() {
		super();
	}

	// Simple CRUD methods

	public Event create() {
		final Event res = new Event();

		res.setAlumns(new ArrayList<Alumn>());
		return res;

	}

	public Collection<Event> findAll() {
		return this.eventRepository.findAll();
	}

	public Event findOne(final int id) {
		Event result;
		result = this.eventRepository.findOne(id);
		return result;
	}

	public Event save(final Event event) {
		return this.eventRepository.save(event);
	}

	public void delete(final Event event) {
		Assert.notNull(event);
		Assert.isTrue(event.getDanceSchool().getManager().equals(this.managerService.findByPrincipal()));
		Assert.isTrue(event.getAlumns().size() == 0);

		this.eventRepository.delete(event);
	}

	public Event newOrEditEvent(final Event event) {
		Assert.notNull(event);
		Assert.isTrue(event.getDanceSchool().getManager().equals(this.managerService.findByPrincipal()));
		return this.save(event);
	}
	public Collection<Event> eventsByDanceSchoolId(final int danceSchoolId) {
		return this.eventRepository.eventsByDanceSchoolId(danceSchoolId);
	}

	public Event reconstruct(final Event event, final BindingResult binding) {
		final Event res;

		res = event;
		res.setAlumns(new ArrayList<Alumn>());

		this.validator.validate(res, binding);

		return res;

	}

	public Event editReconstruct(final Event event) {
		final Event res;

		res = this.findOne(event.getId());

		res.setDescription(event.getDescription());
		res.setDuration(event.getDuration());
		res.setMaxAlumns(event.getMaxAlumns());
		res.setStartDate(event.getStartDate());
		res.setTitle(event.getTitle());

		return res;

	}

}
