package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import fr.inativ.mob.horodatage.Event;

public class ArchivedTypeEvent extends Event<TypeEventVisitor<?>> {
    public ArchivedTypeEvent(UUID id) {
        super(id);
    }

    @Override
    public void accept(TypeEventVisitor<?> visitor) {
        visitor.visitForArchive(this);
    }

}