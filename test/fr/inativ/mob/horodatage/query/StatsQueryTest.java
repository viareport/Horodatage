package fr.inativ.mob.horodatage.query;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.inativ.mob.horodatage.Event;
import fr.inativ.mob.horodatage.domain.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TranslatedTypeEvent;
import fr.inativ.mob.horodatage.util.EventSourcingTestRule;
import fr.inativ.mob.horodatage.util.TimeMachine;

public class StatsQueryTest {
    @Rule
    public EventSourcingTestRule evtSrc = new EventSourcingTestRule();

    @Test
    public void test_chronology_stats() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Instant twoDaysAgo = Instant.now().minus(Duration.ofDays(2));
        TimeMachine.useFixedClockAt(twoDaysAgo);
        Event firstEvent = new CreatedTypeEvent(id, "", "");

        Instant yesterday = Instant.now().minus(Duration.ofDays(1));
        TimeMachine.useFixedClockAt(yesterday);
        Event secondEvent = new TranslatedTypeEvent(id, "L");

        Instant now = Instant.now();
        TimeMachine.useSystemDefaultZoneClock();
        Event thirdEvent = new ArchivedTypeEvent(id);

        evtSrc.givenEvents(firstEvent, secondEvent, thirdEvent);

        // WHEN
        StatsProjection resultTwoDaysAgo = StatsQuery.get().getByDate(twoDaysAgo);
        StatsProjection resultYesterday = StatsQuery.get().getByDate(yesterday);
        StatsProjection resultToday = StatsQuery.get().getByDate(now);

        // THEN
        // Au commencement ...
        Assert.assertNotNull(resultTwoDaysAgo);
        Assert.assertEquals(1, resultTwoDaysAgo.nbCreation);
        Assert.assertEquals(0, resultTwoDaysAgo.nbModification);
        Assert.assertEquals(0, resultTwoDaysAgo.nbSuppression);

        // Puis ...
        Assert.assertEquals(1, resultYesterday.nbCreation);
        Assert.assertEquals(1, resultYesterday.nbModification);
        Assert.assertEquals(0, resultYesterday.nbSuppression);

        // Et enfin ...
        Assert.assertEquals(1, resultToday.nbCreation);
        Assert.assertEquals(1, resultToday.nbModification);
        Assert.assertEquals(1, resultToday.nbSuppression);
    }
}
