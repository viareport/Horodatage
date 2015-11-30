package fr.inativ.mob.horodatage.domain;

import fr.inativ.mob.horodatage.Event;

import java.util.UUID;

public class TranslatedTypeEvent extends Event<Type>{
	public final String label;

	public TranslatedTypeEvent(UUID id, String label) {
		super(id);
		this.label = label;
	}

	@Override
	public void process(Type type) {
		type.label = this.label;
	}
}
