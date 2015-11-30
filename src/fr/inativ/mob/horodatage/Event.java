package fr.inativ.mob.horodatage;

import fr.inativ.mob.horodatage.util.TimeMachine;

import java.time.Instant;
import java.util.UUID;

public abstract class Event<T> {
    public final Instant date;
    public final UUID id;

    protected Event(UUID id) {
        this.id = id;
        this.date = TimeMachine.now();
    }

    public abstract void process(T object);
}
