package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import fr.inativ.mob.horodatage.util.EventSourcingTestRule;

public class TypeRepositoryTest {
    @Rule
    public EventSourcingTestRule evtSrc = new EventSourcingTestRule();

    @Test
    public void test_load_empty_repository_with_existing_events() {
        // GIVEN (on n'utilise plus la commande pour le given)
        UUID id = UUID.randomUUID();
        evtSrc.givenEvents(new CreatedTypeEvent(id, "toto", "leLabel"), new ArchivedTypeEvent(id));

        // WHEN
        Type actual = TypeRepository.get().findById(id);

        // THEN
        Assert.assertNotNull(actual);
        Assert.assertEquals("toto", actual.code); // From CreatedTypeEvent
        Assert.assertEquals("leLabel", actual.label); // From CreatedTypeEvent
        Assert.assertEquals(true, actual.archived); // From ArchivedTypeEvent
    }

}
