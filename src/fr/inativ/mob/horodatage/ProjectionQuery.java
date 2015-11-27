package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ProjectionQuery {
	public static final Map<UUID, TypeProjection> ACTUAL_PROJECTIONS = new HashMap<>();

	public TypeProjection getById(UUID id) {
		TypeProjection typeProjection = ACTUAL_PROJECTIONS.get(id);
		return ACTUAL_PROJECTIONS.get(id);
	}

	public void add(TypeProjection typeProjection) {
		ACTUAL_PROJECTIONS.put(typeProjection.id, typeProjection);
	}

	public void clear() {
		ACTUAL_PROJECTIONS.clear();
	}

	public void removeById(UUID id) {
		ACTUAL_PROJECTIONS.remove(id);
	}

	public TypeProjection getByIdAndDate(UUID id, Instant date, EventStore store) {
		TypeProjection current = null;
		List<Event> events = store.getEventsUntil(date);
		for (Event event : events) {
			current = ProjectionHandler.process(event, current);
		}
		return current;
	}

}
