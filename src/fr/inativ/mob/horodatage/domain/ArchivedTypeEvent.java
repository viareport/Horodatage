package fr.inativ.mob.horodatage.domain;

import fr.inativ.mob.horodatage.Event;

import java.util.UUID;

public class ArchivedTypeEvent extends Event<Type> {
    public ArchivedTypeEvent(UUID id) {
        super(id);
    }

    @Override
    public void process(Type type) {
        type.archived = true;
    }

}
