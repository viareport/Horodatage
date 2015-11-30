package fr.inativ.mob.horodatage.domain;

import java.util.UUID;

public class Type {
	public final UUID id;
	public final String code;
	public String label;
	public boolean archived;

	public Type(String code) {
		this.code = code;
		this.id = UUID.randomUUID();
	}

}
