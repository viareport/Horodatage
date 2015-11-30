package fr.inativ.mob.horodatage.query;

import java.time.Instant;
import java.util.UUID;

import fr.inativ.mob.horodatage.Projection;

public class TypeDetailProjection implements Projection {
	public UUID id;
	public String code, libelle;

	public Instant creation;
	public Instant lastModification;

	public TypeDetailProjection() {
	}
}
