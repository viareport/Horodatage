package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventStore {
	// TODO list des events ordonn�s chronologiquement
	private List<Event> store;
	private ProjectionHandler projector;

	EventStore() {
		this.store = new ArrayList<>();
	}

	void store(Event event) {
		this.store.add(event);
		// TODO Id�alement on devrait avoir plusieurs ProjectionHandler et
		// appeler leur m�thode 'notify'
		this.projector.notify(event);
		this.projector.notifyStats(event);
	}

	public void register(ProjectionHandler projector) {
		this.projector = projector;
	}

	public void clear() {
		store.clear();
	}

	public List<Event> getEventsUntil(Instant date) {
		return store.stream().filter(event -> event.date.equals(date) || event.date.isBefore(date))
				.collect(Collectors.toList());
	}
}
