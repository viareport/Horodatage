package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import fr.inativ.mob.horodatage.Event;

public class TranslatedTypeEvent extends Event<TypeEventVisitor<?>> {
    public final String label;

    public TranslatedTypeEvent(UUID id, String label) {
        super(id);
        this.label = label;
    }

    @Override
    public void accept(TypeEventVisitor<?> visitor) {
        visitor.visitForTranslate(this);
    }
}
