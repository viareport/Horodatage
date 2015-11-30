package fr.inativ.mob.horodatage.event;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.domain.Type;

public class CreatedTypeEvent extends Event {
    public final Type type;

    public CreatedTypeEvent(Type type) {
        super(type.id);
        this.type = type;
    }
}
