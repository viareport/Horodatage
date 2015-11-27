package fr.inativ.mob.horodatage;

import java.time.Duration;
import java.time.Instant;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TypeCommandTest {
	private EventStore eventStore;
	private ProjectionQuery query;

	@Before
	public void setUp() {
		eventStore = new EventStore();
		query = new ProjectionQuery();

		ProjectionHandler projector = new ProjectionHandler(query);
		eventStore.register(projector);

		TimeMachine.useSystemDefaultZoneClock();
	}

	@After
	public void tearDown() {
		eventStore.clear();
		query.clear();
	}

	@Test
	public void test_creation_projection() {
		CreatedTypeEvent createdEvent = TypeCommand.create("toto");
		eventStore.store(createdEvent);

		TypeProjection result = query.getById(createdEvent.id);

		Assert.assertNotNull(result);
		Assert.assertEquals("toto", result.code);
		Assert.assertNotNull(result.creation);
		Assert.assertNull(result.lastModification);
	}

	@Test
	public void test_translation_projection() {
		CreatedTypeEvent createdEvent = TypeCommand.create("toto");
		eventStore.store(createdEvent);

		// TODO type -> id
		TranslatedTypeEvent translatedEvent = TypeCommand.translate(createdEvent.type, "Label");
		eventStore.store(translatedEvent);

		TypeProjection result = query.getById(createdEvent.id);

		Assert.assertNotNull(result);
		Assert.assertEquals("toto", result.code);
		Assert.assertEquals("Label", result.libelle);
		Assert.assertNotNull(result.creation);
		Assert.assertNotNull(result.lastModification);
	}

	@Test
	public void test_archive_projection() {
		CreatedTypeEvent createdEvent = TypeCommand.create("toto");
		eventStore.store(createdEvent);

		// TODO type -> id
		ArchivedTypeEvent archivedTypeEvent = TypeCommand.archive(createdEvent.type);
		eventStore.store(archivedTypeEvent);

		TypeProjection result = query.getById(createdEvent.id);

		Assert.assertNull(result);
	}

	@Test
	public void test_chronology_projection() {
		Instant beforeYesterday = Instant.now().minus(Duration.ofDays(2));
		TimeMachine.useFixedClockAt(beforeYesterday);
		CreatedTypeEvent createdEvent = TypeCommand.create("toto");
		eventStore.store(createdEvent);

		// TODO type -> id
		Instant yesterday = Instant.now().minus(Duration.ofDays(1));
		TimeMachine.useFixedClockAt(yesterday);
		TranslatedTypeEvent translatedEvent = TypeCommand.translate(createdEvent.type, "Label");
		eventStore.store(translatedEvent);

		// TODO type -> id
		Instant now = Instant.now();
		TimeMachine.useSystemDefaultZoneClock();
		ArchivedTypeEvent archivedTypeEvent = TypeCommand.archive(createdEvent.type);
		eventStore.store(archivedTypeEvent);

		// Au commencement ...
		TypeProjection result = query.getByIdAndDate(createdEvent.id, beforeYesterday, eventStore);
		Assert.assertNotNull(result);
		Assert.assertEquals("toto", result.code);
		Assert.assertNotNull(result.creation);
		Assert.assertNull(result.lastModification);

		// Puis ...
		result = query.getByIdAndDate(createdEvent.id, yesterday, eventStore);
		Assert.assertNotNull(result);
		Assert.assertEquals("toto", result.code);
		Assert.assertEquals("Label", result.libelle);
		Assert.assertNotNull(result.creation);
		Assert.assertNotNull(result.lastModification);

		// Et enfin ...
		result = query.getByIdAndDate(createdEvent.id, now, eventStore);
		Assert.assertNull(result);
	}
}
