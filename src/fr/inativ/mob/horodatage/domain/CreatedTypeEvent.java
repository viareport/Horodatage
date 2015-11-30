package fr.inativ.mob.horodatage.domain;

import fr.inativ.mob.horodatage.Event;

import java.util.UUID;

public class CreatedTypeEvent extends Event<Type> {
    public final String code, label;

    public CreatedTypeEvent(UUID id, String code, String label) {
        super(id);
        this.code = code;
        this.label = label;
    }

    @Override
    public void process(Type type) {
        type.code = this.code;
        type.id = this.id;
        type.label = this.label;
    }
}
