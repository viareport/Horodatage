package fr.inativ.mob.horodatage;

// Conversion event -> projection
public class ProjectionHandler {

	private ProjectionQuery query;

	public ProjectionHandler(ProjectionQuery query) {
		this.query = query;
	}

	public void notify(Event event) {
		TypeProjection typeProjection = null;
		if (event instanceof CreatedTypeEvent) {
			CreatedTypeEvent createdTypeEvent = (CreatedTypeEvent) event;
			typeProjection = new TypeProjection(createdTypeEvent.type.id, createdTypeEvent.type.code,
					createdTypeEvent.type.label);
			typeProjection.creation = createdTypeEvent.date;
			this.query.add(typeProjection);
		}

		if (event instanceof TranslatedTypeEvent) {
			TranslatedTypeEvent translatedEvent = (TranslatedTypeEvent) event;
			typeProjection = this.query.getById(translatedEvent.id);
			typeProjection.libelle = translatedEvent.label;
			typeProjection.lastModification = translatedEvent.date;
		}

		if (event instanceof ArchivedTypeEvent) {
			ArchivedTypeEvent archivedTypeEvent = (ArchivedTypeEvent) event;
			query.removeById(archivedTypeEvent.id);
		}
	}

	static TypeProjection process(Event event, TypeProjection current) {
		TypeProjection calculatedProjection = null;
		if (event instanceof CreatedTypeEvent) {
			calculatedProjection = new TypeProjection();
			CreatedTypeEvent createdTypeEvent = (CreatedTypeEvent) event;
			calculatedProjection.code = createdTypeEvent.type.code;
			calculatedProjection.id = createdTypeEvent.type.id;
			calculatedProjection.libelle = createdTypeEvent.type.label;
			calculatedProjection.creation = createdTypeEvent.date;
		}

		if (event instanceof TranslatedTypeEvent) {
			TranslatedTypeEvent translatedEvent = (TranslatedTypeEvent) event;

			calculatedProjection = current;
			calculatedProjection.libelle = translatedEvent.label;
			calculatedProjection.lastModification = translatedEvent.date;
		}

		if (event instanceof ArchivedTypeEvent) {
			calculatedProjection = null;
		}
		return calculatedProjection;
	}

}
