package fr.inativ.mob.horodatage.query;

import java.time.Instant;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventHandler;
import fr.inativ.mob.horodatage.util.EventStore;

public class StatsQuery implements EventHandler<Event> {
    private static final StatsQuery INSTANCE = new StatsQuery();

    private static StatsProjection current = new StatsProjection();
    private final StatsProjector projector;

    private StatsQuery() {
        projector = new StatsProjector();
    }

    public static StatsQuery get() {
        return INSTANCE;
    }

    public StatsProjection getByDate(Instant date) {
        StatsProjection current = new StatsProjection();
        for (Event evt : EventStore.get().getEventsUntil(date)) {
            current = projector.process(current, evt);
        }
        return current;
    }

    // Pour tests
    public void clear() {
        current = new StatsProjection();
    }

    @Override
    public void handle(Event evt) {
        current = projector.process(current, evt);
    }

}
