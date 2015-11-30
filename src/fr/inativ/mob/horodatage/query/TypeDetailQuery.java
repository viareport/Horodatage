package fr.inativ.mob.horodatage.query;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventHandler;
import fr.inativ.mob.horodatage.util.EventStore;

public class TypeDetailQuery implements EventHandler<Event> {
    private static final TypeDetailQuery INSTANCE = new TypeDetailQuery();

    private static final Map<UUID, TypeDetailProjection> ACTUAL_PROJECTIONS = new HashMap<>();
    private final TypeDetailProjector projector;

    private TypeDetailQuery() {
        projector = new TypeDetailProjector();
    }

    public static final TypeDetailQuery get() {
        return INSTANCE;
    }

    public TypeDetailProjection getById(UUID id) {
        TypeDetailProjection typeProjection = ACTUAL_PROJECTIONS.get(id);
        return ACTUAL_PROJECTIONS.get(id);
    }

    // Pour tests
    public void clear() {
        ACTUAL_PROJECTIONS.clear();
    }

    public TypeDetailProjection getByIdAndDate(UUID id, Instant date) {
        TypeDetailProjection current = null;
        for (Event event : EventStore.get().getEventsUntil(date)) {
            current = projector.process(current, event);
        }
        return current;
    }

    @Override
    public void handle(Event evt) {
        TypeDetailProjection lastState = ACTUAL_PROJECTIONS.get(evt.id);

        TypeDetailProjection newState = projector.process(lastState, evt);
        if (newState == null) {
            ACTUAL_PROJECTIONS.remove(evt.id);
        } else {
            ACTUAL_PROJECTIONS.put(newState.id, newState);
        }
    }
}
