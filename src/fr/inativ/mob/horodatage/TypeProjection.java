package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.UUID;

public class TypeProjection {
	public UUID id;
	public String code, libelle;

	public Instant creation;
	public Instant lastModification;

	public TypeProjection(UUID id, String code, String libelle) {
		super();
		this.id = id;
		this.code = code;
		this.libelle = libelle;
	}

	public TypeProjection() {
	}
}
