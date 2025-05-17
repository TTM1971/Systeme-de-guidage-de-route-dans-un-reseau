package labo;

import javafx.scene.shape.Line;
import labo.Intersection;

public class Route {
    private final Intersection a;
    private final Intersection b;
    private int poidsActuel;
    private final int poidsOriginal;
    private Line ligneVisuelle;

    public Route(Intersection a, Intersection b, int poids) {
        if (a == null || b == null) 
            throw new IllegalArgumentException("Les intersections ne peuvent pas être nulles");
        if (poids < 0) 
            throw new IllegalArgumentException("Le poids doit être positif ou nul");

        this.a = a;
        this.b = b;
        this.poidsOriginal = poids;
        this.poidsActuel = poids;
    }

    // --- Getters standard ---
    public Intersection getA() { return a; }
    public Intersection getB() { return b; }
    public int getWeight() { return poidsActuel; }
    public Line getLine() { return ligneVisuelle; }

    // --- Gestion des événements ---
    public void appliquerImpact(int multiplicateur) {
        if (multiplicateur < 1) {
            throw new IllegalArgumentException("Le multiplicateur doit être >= 1");
        }
        this.poidsActuel = poidsOriginal * multiplicateur;
    }

    public void reinitialiserPoids() {
        this.poidsActuel = poidsOriginal;
    }

    // --- Méthodes utilitaires ---
    public boolean relie(Intersection x, Intersection y) {
        return (a.equals(x) && b.equals(y)) || (a.equals(y) && b.equals(x));
    }
    
    public int getPoidsOriginal() {
        return this.poidsOriginal;
    }
    
    public int getPoidsActuel() {
        return this.poidsActuel;
    }

    public Intersection getAutreIntersection(Intersection inter) {
        if (inter.equals(a)) return b;
        if (inter.equals(b)) return a;
        throw new IllegalArgumentException("Intersection non reliée à cette route");
    }

    public void setLine(Line line) {
        this.ligneVisuelle = line;
    }

    @Override
    public String toString() {
        return String.format("Route %s ↔ %s (poids: %d/%d)", 
               a.getNom(), b.getNom(), poidsActuel, poidsOriginal);
    }
}