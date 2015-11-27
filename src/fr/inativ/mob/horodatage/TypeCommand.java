package fr.inativ.mob.horodatage;

public class TypeCommand {

	public static CreatedTypeEvent create(String code) {
		return new CreatedTypeEvent(new Type(code));
	}

	public static ArchivedTypeEvent archive(Type type) {
		type.archived = true;
		return new ArchivedTypeEvent(type.id);
	}

	public static TranslatedTypeEvent translate(Type type, String label) {
		type.label = label;
		return new TranslatedTypeEvent(type.id, label);
	}
}
