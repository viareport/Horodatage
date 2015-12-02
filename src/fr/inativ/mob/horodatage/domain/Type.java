package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import fr.inativ.mob.horodatage.Event;

public class Type {
    public UUID id;
    public String code;
    public String label;
    public boolean archived = false;

    Type() {
        this.code = null;
        this.id = null;
    }

    public Type(String code) {
        this.code = code;
        this.id = UUID.randomUUID();
    }

    void apply(Event evt) {
        evt.accept(new TypeEventVisitor<Type>(this) {
            @Override
            public void visitForCreate(CreatedTypeEvent e) {
                visited.code = e.code;
                visited.id = e.id;
                visited.label = e.label;
            }

            @Override
            public void visitForArchive(ArchivedTypeEvent e) {
                visited.archived = true;
            }

            @Override
            public void visitForTranslate(TranslatedTypeEvent e) {
                visited.label = e.label;
            }
        });

    }
}
