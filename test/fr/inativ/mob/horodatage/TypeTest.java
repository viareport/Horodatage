package fr.inativ.mob.horodatage;

import java.time.Duration;
import java.time.Instant;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import fr.inativ.mob.horodatage.command.TypeCommand;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.query.StatsProjection;
import fr.inativ.mob.horodatage.query.StatsQuery;
import fr.inativ.mob.horodatage.query.TypeDetailProjection;
import fr.inativ.mob.horodatage.query.TypeDetailQuery;
import fr.inativ.mob.horodatage.util.EventBus;
import fr.inativ.mob.horodatage.util.EventStore;
import fr.inativ.mob.horodatage.util.TimeMachine;

public class TypeTest {

    @Before
    public void setUp() {
        EventBus.register(EventStore.get(), StatsQuery.get(), TypeDetailQuery.get());

        TimeMachine.useSystemDefaultZoneClock();
    }

    @After
    public void tearDown() {
        EventStore.get().clear();
        TypeDetailQuery.get().clear();
        StatsQuery.get().clear();
    }

    @Test
    public void test_creation_projection() {
        CreatedTypeEvent createdEvent = TypeCommand.create("toto");

        TypeDetailProjection result = TypeDetailQuery.get().getById(createdEvent.id);

        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertNotNull(result.creation);
        Assert.assertNull(result.lastModification);
    }

    @Test
    public void test_translation_projection() {
        CreatedTypeEvent createdEvent = TypeCommand.create("toto");

        // TODO type -> id
        TypeCommand.translate(createdEvent.id, "Label");

        TypeDetailProjection result = TypeDetailQuery.get().getById(createdEvent.id);

        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertEquals("Label", result.libelle);
        Assert.assertNotNull(result.creation);
        Assert.assertNotNull(result.lastModification);
    }

    @Test
    public void test_archive_projection() {
        CreatedTypeEvent createdEvent = TypeCommand.create("toto");

        // TODO type -> id
        TypeCommand.archive(createdEvent.id);

        TypeDetailProjection result = TypeDetailQuery.get().getById(createdEvent.id);

        Assert.assertNull(result);
    }

    @Test
    public void test_chronology_projection() {
        Instant beforeYesterday = Instant.now().minus(Duration.ofDays(2));
        TimeMachine.useFixedClockAt(beforeYesterday);
        CreatedTypeEvent createdEvent = TypeCommand.create("toto");

        // TODO type -> id
        Instant yesterday = Instant.now().minus(Duration.ofDays(1));
        TimeMachine.useFixedClockAt(yesterday);
        TypeCommand.translate(createdEvent.id, "Label");

        // TODO type -> id
        Instant now = Instant.now();
        TimeMachine.useSystemDefaultZoneClock();
        TypeCommand.archive(createdEvent.id);

        // Au commencement ...
        TypeDetailProjection result = TypeDetailQuery.get().getByIdAndDate(createdEvent.id, beforeYesterday);
        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertNotNull(result.creation);
        Assert.assertNull(result.lastModification);

        // Puis ...
        result = TypeDetailQuery.get().getByIdAndDate(createdEvent.id, yesterday);
        Assert.assertNotNull(result);
        Assert.assertEquals("toto", result.code);
        Assert.assertEquals("Label", result.libelle);
        Assert.assertNotNull(result.creation);
        Assert.assertNotNull(result.lastModification);

        // Et enfin ...
        result = TypeDetailQuery.get().getByIdAndDate(createdEvent.id, now);
        Assert.assertNull(result);
    }

    @Test
    public void test_chronology_stats() {
        Instant beforeYesterday = Instant.now().minus(Duration.ofDays(2));
        TimeMachine.useFixedClockAt(beforeYesterday);
        CreatedTypeEvent createdEvent = TypeCommand.create("toto");

        // TODO type -> id
        Instant yesterday = Instant.now().minus(Duration.ofDays(1));
        TimeMachine.useFixedClockAt(yesterday);
        TypeCommand.translate(createdEvent.id, "Label");

        // TODO type -> id
        Instant now = Instant.now();
        TimeMachine.useSystemDefaultZoneClock();
        TypeCommand.archive(createdEvent.id);

        // Au commencement ...
        StatsProjection result = StatsQuery.get().getByDate(beforeYesterday);
        Assert.assertNotNull(result);
        Assert.assertEquals(1, result.nbCreation);
        Assert.assertEquals(0, result.nbModification);
        Assert.assertEquals(0, result.nbSuppression);

        // Puis ...
        result = StatsQuery.get().getByDate(yesterday);
        Assert.assertEquals(1, result.nbCreation);
        Assert.assertEquals(1, result.nbModification);
        Assert.assertEquals(0, result.nbSuppression);

        // Et enfin ...
        result = StatsQuery.get().getByDate(now);
        Assert.assertEquals(1, result.nbCreation);
        Assert.assertEquals(1, result.nbModification);
        Assert.assertEquals(1, result.nbSuppression);
    }
}
