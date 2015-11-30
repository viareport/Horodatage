package fr.inativ.mob.horodatage.util;

import java.time.Instant;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventHandler;

public class EventStore implements EventHandler<Event> {
    private static final EventStore STORE = new EventStore();

    // TODO list des events ordonnes chronologiquement
    private Multimap<UUID, Event> store;

    private EventStore() {
        this.store = ArrayListMultimap.create();
    }

    public void clear() {
        store.clear();
    }

    public Iterable<Event> getEventsUntil(UUID id, Instant date) {
        return store.get(id).stream().filter(event -> event.date.equals(date) || event.date.isBefore(date))
            .collect(Collectors.toList());
    }

    public Iterable<Event> getEventsUntil(Instant date) {
        return store.values().stream().filter(event -> event.date.equals(date) || event.date.isBefore(date))
            .collect(Collectors.toList());
    }

    public static EventStore get() {
        return STORE;
    }

    @Override
    public void handle(Event evt) {
        this.store.put(evt.id, evt);
    }
}
