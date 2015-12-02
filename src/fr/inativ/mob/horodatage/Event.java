package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.UUID;

import fr.inativ.mob.horodatage.util.TimeMachine;

public abstract class Event<V extends EventVisitor<?>> {
    public final Instant date;
    public final UUID id;

    protected Event(UUID id) {
        this.id = id;
        this.date = TimeMachine.now();
    }

    public abstract void accept(V visitor);
}
