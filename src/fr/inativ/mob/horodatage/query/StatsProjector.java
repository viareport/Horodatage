package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.Projector;
import fr.inativ.mob.horodatage.domain.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TranslatedTypeEvent;

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
