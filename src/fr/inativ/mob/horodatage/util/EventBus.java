package fr.inativ.mob.horodatage.util;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.EventHandler;

public class EventBus {

    private final static com.google.common.eventbus.EventBus EVENT_BUS = new com.google.common.eventbus.EventBus();

    private EventBus() {
    }

    public static void register(EventHandler<?>... subscribers) {
        for (Object subscriber : subscribers) {
            EVENT_BUS.register(subscriber);
        }
    }

    public static void publishEvent(Event event) {
        EVENT_BUS.post(event);
    }
}
