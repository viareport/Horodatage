package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

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

    /*void apply(Event evt) {
        if (evt instanceof CreatedTypeEvent) {
            CreatedTypeEvent createdTypeEvent = (CreatedTypeEvent) evt;
            this.code = createdTypeEvent.code;
            this.id = createdTypeEvent.id;
            this.label = createdTypeEvent.label;
        }

        if (evt instanceof TranslatedTypeEvent) {
            TranslatedTypeEvent translatedEvent = (TranslatedTypeEvent) evt;
            this.label = translatedEvent.label;
        }

        if (evt instanceof ArchivedTypeEvent) {
            this.archived = true;
        }
    }*/

}
