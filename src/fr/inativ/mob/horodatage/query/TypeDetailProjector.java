package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventVisitor;
import fr.inativ.mob.horodatage.Projector;
import fr.inativ.mob.horodatage.domain.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TranslatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TypeEventVisitor;

class TypeDetailProjector
    implements Projector<Event<EventVisitor<TypeDetailProjection>>, TypeDetailProjection> {

    @Override
    public TypeDetailProjection process(TypeDetailProjection currentState,
        Event<EventVisitor<TypeDetailProjection>> evt) {
        TypeEventVisitor<TypeDetailProjection> visitor = new TypeEventVisitor<TypeDetailProjection>(
            currentState) {

            @Override
            public void visitForCreate(CreatedTypeEvent e) {
                visited = new TypeDetailProjection();
                visited.code = e.code;
                visited.id = e.id;
                visited.libelle = e.label;
                visited.creation = e.date;
            }

            @Override
            public void visitForArchive(ArchivedTypeEvent e) {
                visited = null;
            }

            @Override
            public void visitForTranslate(TranslatedTypeEvent e) {
                visited.libelle = e.label;
                visited.lastModification = e.date;
            }

        };
        evt.accept(visitor);
        return visitor.getVisited();
    }

}
