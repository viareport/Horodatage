package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.inativ.mob.horodatage.util.EventSourcingTestRule;

public class TypeTest {
    @Rule
    public EventSourcingTestRule evtSrc = new EventSourcingTestRule();

    public void test_new_type_is_immatriculated() {
        // GIVEN
        Type type = new Type("");

        // WHEN
        // THEN
        Assert.assertNotNull(type.id);
    }

    @Test
    public void test_new_type_is_not_archived() {
        // GIVEN
        Type type = new Type("");

        // WHEN
        // THEN
        Assert.assertFalse(type.archived);
    }

    @Test
    public void test_apply_creation_event() {
        // GIVEN
        UUID id = UUID.randomUUID();
        Type type = new Type();

        // WHEN
        type.apply(new CreatedTypeEvent(id, "toto", "leLabel"));

        // THEN
        Assert.assertEquals(id, type.id);
        Assert.assertEquals("toto", type.code);
        Assert.assertEquals("leLabel", type.label);
        Assert.assertFalse(type.archived);
    }

    @Test
    public void test_apply_translation_event() {
        // GIVEN
        Type type = new Type("");

        // WHEN
        type.apply(new TranslatedTypeEvent(type.id, "leLabel"));

        // THEN
        Assert.assertEquals("leLabel", type.label);
    }

    @Test
    public void test_apply_archiving_event() {
        // GIVEN
        Type type = new Type("");

        // WHEN
        type.apply(new ArchivedTypeEvent(type.id));

        // THEN
        Assert.assertTrue(type.archived);
    }
}
