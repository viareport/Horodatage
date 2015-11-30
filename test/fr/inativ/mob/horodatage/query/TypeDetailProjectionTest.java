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

public class TypeDetailProjectionTest {
    @Rule
    public EventSourcingTestRule evtSrc = new EventSourcingTestRule();

    @Test
    public void test_creation_projection() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null));

        // WHEN
        TypeDetailProjection result = TypeDetailQuery.get().getById(id);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertNotNull(result.creation);
        Assert.assertNull(result.lastModification);
    }

    @Test
    public void test_translation_projection() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null), new TranslatedTypeEvent(id, "Label"));

        // WHEN
        TypeDetailProjection result = TypeDetailQuery.get().getById(id);

        // THEN
        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertEquals("Label", result.libelle);
        Assert.assertNotNull(result.creation);
        Assert.assertNotNull(result.lastModification);
    }

    @Test
    public void test_archive_projection() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null), new ArchivedTypeEvent(id));

        // WHEN
        TypeDetailProjection result = TypeDetailQuery.get().getById(id);

        // THEN
        Assert.assertNull(result);
    }

    @Test
    public void test_chronology_projection() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Instant twoDaysAgo = Instant.now().minus(Duration.ofDays(2));
        TimeMachine.useFixedClockAt(twoDaysAgo);
        Event firstEvent = new CreatedTypeEvent(id, "toto", "");

        Instant yesterday = Instant.now().minus(Duration.ofDays(1));
        TimeMachine.useFixedClockAt(yesterday);
        Event secondEvent = new TranslatedTypeEvent(id, "Label");

        Instant now = Instant.now();
        TimeMachine.useSystemDefaultZoneClock();
        Event thirdEvent = new ArchivedTypeEvent(id);

        evtSrc.givenEvents(firstEvent, secondEvent, thirdEvent);

        // WHEN
        TypeDetailProjection resultTwoDaysAgo = TypeDetailQuery.get().getByIdAndDate(id, twoDaysAgo);
        TypeDetailProjection resultYesterday = TypeDetailQuery.get().getByIdAndDate(id, yesterday);
        TypeDetailProjection resultToday = TypeDetailQuery.get().getByIdAndDate(id, now);

        // THEN
        // Au commencement ...
        Assert.assertNotNull(resultTwoDaysAgo);
        Assert.assertEquals("toto", resultTwoDaysAgo.code);
        Assert.assertNotNull(resultTwoDaysAgo.creation);
        Assert.assertNull(resultTwoDaysAgo.lastModification);

        // Puis ...
        Assert.assertNotNull(resultYesterday);
        Assert.assertEquals("toto", resultYesterday.code);
        Assert.assertEquals("Label", resultYesterday.libelle);
        Assert.assertNotNull(resultYesterday.creation);
        Assert.assertNotNull(resultYesterday.lastModification);

        // Et enfin ...
        Assert.assertNull(resultToday);
    }
}
