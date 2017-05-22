
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Event;
import repositories.EventRepository;

@Service
@Transactional
public class EventService {

	@Autowired
	private EventRepository eventRepository;


	// Constructor
	public EventService() {
		super();
	}

	// Simple CRUD methods

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
}
