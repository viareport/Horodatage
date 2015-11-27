package fr.inativ.mob.horodatage;

import java.util.UUID;

public class TranslatedTypeEvent extends Event{
	public final String label;

	public TranslatedTypeEvent(UUID id, String label) {
		super(id);
		this.label = label;
	}

}
