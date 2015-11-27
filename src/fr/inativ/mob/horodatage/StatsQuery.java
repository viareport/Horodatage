package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StatsQuery {
	public static final StatsProjection current = new StatsProjection();

	public static StatsProjection get() {
		return current;
	}

	public static StatsProjection getByDate(Instant date, EventStore store) {
		StatsProjection current = null;
		List<Event> events = store.getEventsUntil(date);
		for (Event event : events) {
			current = ProjectionHandler.processStats(event, current);
		}
		return current;
	}

}
