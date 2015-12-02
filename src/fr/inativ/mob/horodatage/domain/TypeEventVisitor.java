package fr.inativ.mob.horodatage.domain;

import fr.inativ.mob.horodatage.EventVisitor;

public abstract class TypeEventVisitor<T> implements EventVisitor<T> {

    protected T visited;

    public TypeEventVisitor(T visited) {
        this.visited = visited;
    }

    public T getVisited() {
        return visited;
    }

    public abstract void visitForCreate(CreatedTypeEvent e);

    public abstract void visitForArchive(ArchivedTypeEvent e);

    public abstract void visitForTranslate(TranslatedTypeEvent e);
}