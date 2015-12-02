package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import fr.inativ.mob.horodatage.Event;

public class CreatedTypeEvent extends Event<TypeEventVisitor<?>> {
    public final String code, label;

    public CreatedTypeEvent(UUID id, String code, String label) {
        super(id);
        this.code = code;
        this.label = label;
    }

    @Override
    public void accept(TypeEventVisitor<?> visitor) {
        visitor.visitForCreate(this);
    }
}