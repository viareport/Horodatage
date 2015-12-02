package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.Projector;
import fr.inativ.mob.horodatage.domain.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TranslatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TypeEventVisitor;

class StatsProjector implements Projector<Event<TypeEventVisitor<StatsProjection>>, StatsProjection> {

    @Override
    public StatsProjection process(final StatsProjection current,
        Event<TypeEventVisitor<StatsProjection>> evt) {
        TypeEventVisitor<StatsProjection> visitor = new TypeEventVisitor<StatsProjection>(
            current) {

            @Override
            public void visitForCreate(CreatedTypeEvent e) {
                visited.nbCreation = 1;
            }

            @Override
            public void visitForArchive(ArchivedTypeEvent e) {
                visited = current;
                visited.nbSuppression++;
            }

            @Override
            public void visitForTranslate(TranslatedTypeEvent e) {
                visited = current;
                visited.nbModification++;
            }

        };
        evt.accept(visitor);
        return visitor.getVisited();
    }

}
