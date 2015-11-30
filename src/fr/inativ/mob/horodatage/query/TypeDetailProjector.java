package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.Projector;
import fr.inativ.mob.horodatage.event.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.event.CreatedTypeEvent;
import fr.inativ.mob.horodatage.event.TranslatedTypeEvent;

class TypeDetailProjector implements Projector<Event, TypeDetailProjection> {

    @Override
    public TypeDetailProjection process(TypeDetailProjection current, Event evt) {
        TypeDetailProjection calculatedProjection = null;
        if (evt instanceof CreatedTypeEvent) {
            calculatedProjection = new TypeDetailProjection();
            CreatedTypeEvent createdTypeEvent = (CreatedTypeEvent) evt;
            calculatedProjection.code = createdTypeEvent.type.code;
            calculatedProjection.id = createdTypeEvent.type.id;
            calculatedProjection.libelle = createdTypeEvent.type.label;
            calculatedProjection.creation = createdTypeEvent.date;
        }

        if (evt instanceof TranslatedTypeEvent) {
            TranslatedTypeEvent translatedEvent = (TranslatedTypeEvent) evt;

            calculatedProjection = current;
            calculatedProjection.libelle = translatedEvent.label;
            calculatedProjection.lastModification = translatedEvent.date;
        }

        if (evt instanceof ArchivedTypeEvent) {
            calculatedProjection = null;
        }
        return calculatedProjection;
    }

}
