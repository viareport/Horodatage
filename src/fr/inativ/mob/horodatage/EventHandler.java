package fr.inativ.mob.horodatage;

import com.google.common.eventbus.Subscribe;

public interface EventHandler<E extends Event> {
    @Subscribe
    void handle(E evt);
}
