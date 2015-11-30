package fr.inativ.mob.horodatage.event;

import java.util.UUID;

import fr.inativ.mob.horodatage.Event;

public class TranslatedTypeEvent extends Event{
	public final String label;

	public TranslatedTypeEvent(UUID id, String label) {
		super(id);
		this.label = label;
	}

}
