package fr.inativ.mob.horodatage;

import java.time.Instant;
import java.util.UUID;

public abstract class Event {
	public final Instant date;
	public final UUID id;
	protected Event(UUID id){
		this.id = id;
		this.date = TimeMachine.now();
	}
}
