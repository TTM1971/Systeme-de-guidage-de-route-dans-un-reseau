package labo;



public abstract class Evenement {
    protected final labo.Intersection intersection;
    
    public Evenement(labo.Intersection intersection) {
        if (intersection == null) {
            throw new IllegalArgumentException("L'intersection ne peut pas être null");
        }
        this.intersection = intersection;
    }
    
    public labo.Intersection getIntersection() {
        return intersection;
    }
    
    /**
     * Applique l'impact de l'événement sur une route
     * @param route La route à modifier
     */
    public abstract void appliquerEffet(GPS gps);
}






/*
public abstract class Evenement {
    protected Intersection localisation;
    protected Intersection destination;
    protected int impact;

    public Evenement(Intersection localisation, Intersection destination, int impact) {
        this.localisation = localisation;
        this.destination = destination;
        this.impact = impact;
    }

    public abstract String getType();

    // Getters
    public Intersection getLocalisation() { return localisation; }
    public Intersection getDestination() { return destination; }
    public int getImpact() { return impact; }
}
*/