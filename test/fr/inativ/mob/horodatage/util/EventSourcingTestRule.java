package fr.inativ.mob.horodatage.util;

import org.junit.rules.ExternalResource;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.query.StatsQuery;
import fr.inativ.mob.horodatage.query.TypeDetailQuery;

public class EventSourcingTestRule extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        EventBus.register(EventStore.get(), StatsQuery.get(), TypeDetailQuery.get());

        TimeMachine.useSystemDefaultZoneClock();
    }

    @Override
    protected void after() {
        EventStore.get().clear();
        TypeDetailQuery.get().clear();
        StatsQuery.get().clear();
    }

    public void givenEvents(Event... events) {
        for (Event evt : events) {
            EventBus.publishEvent(evt);
        }
    }
}
