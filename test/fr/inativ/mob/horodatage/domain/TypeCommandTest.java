package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.inativ.mob.horodatage.command.TypeCommand;
import fr.inativ.mob.horodatage.util.EventSourcingTestRule;

public class TypeCommandTest {

    @Rule
    public EventSourcingTestRule evtSrc = new EventSourcingTestRule();

    @Test
    public void test_creation_event() {
        // GIVEN

        // WHEN
        CreatedTypeEvent event = TypeCommand.create("toto");

        // THEN
        Assert.assertNotNull(event);
        Assert.assertNotNull(event.id);
        Assert.assertEquals("toto", event.code);
    }

    @Test
    public void test_translation_event() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null));

        // WHEN
        TranslatedTypeEvent event = TypeCommand.translate(id, "Label");

        // THEN
        Assert.assertNotNull(event);
        Assert.assertEquals(id, event.id);
        Assert.assertEquals("Label", event.label);
    }

    @Test
    public void test_archive_event() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null));

        // WHEN
        ArchivedTypeEvent event = TypeCommand.archive(id);

        Assert.assertNotNull(event);
        Assert.assertEquals(id, event.id);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void test_cannot_archive_already_archived_type() {
        // GIVEN
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", null), new ArchivedTypeEvent(id));

        // WHEN
        TypeCommand.archive(id);

        // THEN (see expected)
    }
}
