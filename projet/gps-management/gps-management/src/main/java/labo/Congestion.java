package labo;

public class Congestion extends Evenement {
    private final int niveau; // 0-2

    public Congestion(Intersection intersection, int niveau) {
        super(intersection);
        this.niveau = Math.max(0, Math.min(2, niveau));
    }

    @Override
    public void appliquerEffet(GPS gps) {
        int multiplicateur;
        switch (niveau) {
            case 0:
                multiplicateur = 1; // Pas d'effet
                break;
            case 1:
                multiplicateur = 3; // Augmentation modérée
                break;
            case 2:
                multiplicateur = 30; // Augmentation importante, mais inférieure à un accident
                break;
            default:
                multiplicateur = 1;
        }

        for (Route route : intersection.getRoutes()) {
            route.appliquerImpact(multiplicateur);
        }
    }
}