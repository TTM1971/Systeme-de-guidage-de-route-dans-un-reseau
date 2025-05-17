package labo;

import javafx.scene.paint.Color;

public class Accident extends Evenement {
    public Accident(Intersection intersection) {
        super(intersection);
    }

    @Override
    public void appliquerEffet(GPS gps) {
        for (Route route : intersection.getRoutes()) {
            route.appliquerImpact(50);
            if (route.getLine() != null) {
                route.getLine().setStroke(Color.ORANGERED);
            }
        }
    }

    @Override
    public String toString() {
        return "Accident à " + intersection.getNom();
    }
}