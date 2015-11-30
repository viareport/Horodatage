package fr.inativ.mob.horodatage.command;

import fr.inativ.mob.horodatage.domain.Type;
import fr.inativ.mob.horodatage.event.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.event.CreatedTypeEvent;
import fr.inativ.mob.horodatage.event.TranslatedTypeEvent;
import fr.inativ.mob.horodatage.util.EventBus;

public class TypeCommand {

    public static CreatedTypeEvent create(String code) {
        Type newType = new Type(code);
        CreatedTypeEvent evt = new CreatedTypeEvent(newType);
        EventBus.publishEvent(evt);

        return evt;
    }

    public static ArchivedTypeEvent archive(Type type) {
        type.archived = true;
        ArchivedTypeEvent evt = new ArchivedTypeEvent(type.id);
        EventBus.publishEvent(evt);

        return evt;
    }

    public static TranslatedTypeEvent translate(Type type, String label) {
        type.label = label;
        TranslatedTypeEvent evt = new TranslatedTypeEvent(type.id, label);
        EventBus.publishEvent(evt);

        return evt;
    }
}
