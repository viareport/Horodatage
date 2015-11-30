package fr.inativ.mob.horodatage.query;

import fr.inativ.mob.horodatage.Projection;

public class StatsProjection implements Projection {
    public int nbCreation, nbModification, nbSuppression;

    public StatsProjection() {
        super();
        nbCreation = 0;
        nbModification = 0;
        nbSuppression = 0;
    }

}
