package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.Projector;
import fr.inativ.mob.horodatage.event.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.event.CreatedTypeEvent;
import fr.inativ.mob.horodatage.event.TranslatedTypeEvent;

class StatsProjector implements Projector<Event, StatsProjection> {

    @Override
    public StatsProjection process(StatsProjection current, Event evt) {
        StatsProjection calculatedProjection = new StatsProjection();
        if (evt instanceof CreatedTypeEvent) {
            calculatedProjection.nbCreation = 1;
        }

        if (evt instanceof TranslatedTypeEvent) {
            calculatedProjection = current;
            calculatedProjection.nbModification++;
        }

        if (evt instanceof ArchivedTypeEvent) {
            calculatedProjection = current;
            calculatedProjection.nbSuppression++;
        }
        return calculatedProjection;
    }

}
