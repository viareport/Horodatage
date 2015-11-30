package fr.inativ.mob.horodatage.command;

import java.util.UUID;

import fr.inativ.mob.horodatage.domain.ArchivedTypeEvent;
import fr.inativ.mob.horodatage.domain.CreatedTypeEvent;
import fr.inativ.mob.horodatage.domain.TranslatedTypeEvent;
import fr.inativ.mob.horodatage.domain.Type;
import fr.inativ.mob.horodatage.domain.TypeRepository;
import fr.inativ.mob.horodatage.util.EventBus;

public class TypeCommand {

    public static CreatedTypeEvent create(String code) {
        Type newType = new Type(code);
        TypeRepository.get().save(newType);
        CreatedTypeEvent evt = new CreatedTypeEvent(newType.id, newType.code, newType.label);
        EventBus.publishEvent(evt);

        return evt;
    }

    public static ArchivedTypeEvent archive(UUID typeId) {
        Type existingType = TypeRepository.get().findById(typeId);
        existingType.archived = true;
        TypeRepository.get().save(existingType);

        ArchivedTypeEvent evt = new ArchivedTypeEvent(typeId);
        EventBus.publishEvent(evt);

        return evt;
    }

    public static TranslatedTypeEvent translate(UUID typeId, String label) {
        Type existingType = TypeRepository.get().findById(typeId);
        existingType.label = label;
        TypeRepository.get().save(existingType);

        TranslatedTypeEvent evt = new TranslatedTypeEvent(typeId, label);
        EventBus.publishEvent(evt);

        return evt;
    }
}
