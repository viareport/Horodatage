package fr.inativ.mob.horodatage;

public interface Projector<E extends Event, P extends Projection> {
    P process(P currentState, E evt);
}
