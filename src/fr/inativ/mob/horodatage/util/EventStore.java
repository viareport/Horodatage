package fr.inativ.mob.horodatage.util;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventHandler;

public class EventStore implements EventHandler<Event> {
    // TODO list des events ordonnes chronologiquement
    private List<Event> store;

    private static final EventStore STORE = new EventStore();

    private EventStore() {
        this.store = new ArrayList<>();
    }

    public void clear() {
        store.clear();
    }

    public List<Event> getEventsUntil(Instant date) {
        return store.stream().filter(event -> event.date.equals(date) || event.date.isBefore(date))
            .collect(Collectors.toList());
    }

    public static EventStore get() {
        return STORE;
    }

    @Override
    public void handle(Event evt) {
        this.store.add(evt);
    }
}
