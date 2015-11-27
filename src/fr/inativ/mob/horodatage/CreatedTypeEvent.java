package fr.inativ.mob.horodatage;

public class CreatedTypeEvent extends Event{
	public final Type type;

	CreatedTypeEvent(Type type){
		super(type.id);
		this.type=type;
	}
}
