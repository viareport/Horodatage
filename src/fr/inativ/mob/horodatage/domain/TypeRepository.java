package fr.inativ.mob.horodatage.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.util.EventStore;
import fr.inativ.mob.horodatage.util.TimeMachine;

public class TypeRepository {
    private static final TypeRepository INSTANCE = new TypeRepository();

    private final Map<UUID, Type> memDb;

    private TypeRepository() {
        memDb = new HashMap<>();
    }

    public static final TypeRepository get() {
        return INSTANCE;
    }

    public Type findById(UUID id) {
        Type found = null;
        if (!memDb.containsKey(id)) {
            found = replay(id, EventStore.get().getEventsUntil(TimeMachine.now()));
            memDb.put(id, found);
        }
        return memDb.get(id);
    }

    public void save(Type type) {
        memDb.put(type.id, type);
    }

    private Type replay(UUID id, Collection<Event> events) {
        Type type = new Type();

        if (!events.isEmpty()) {
            for (Event evt : events) {
                type.apply(evt);
            }
        }
        return type;
    }

    public void init() {
        memDb.clear();
    }
}
