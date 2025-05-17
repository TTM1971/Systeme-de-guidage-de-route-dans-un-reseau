package labo;

import java.util.ArrayList;
import java.util.List;

public class Trajet {
    private List<Intersection> listeIntersections;
    private int tempsTotal;
    
    public Trajet() {
        this.listeIntersections = new ArrayList<>();
        this.tempsTotal = 0;
    }
    
    /**
     * Ajoute une intersection au trajet.
     * @param inter Intersection à ajouter
     */
    public void ajouterIntersection(Intersection inter) {
        if (inter == null) {
            throw new IllegalArgumentException("L'intersection ajoutée au trajet ne peut pas être null");
        }
        // Éviter les doublons et limiter la taille
        if (!listeIntersections.contains(inter) && listeIntersections.size() < 1000) {
            listeIntersections.add(inter);
        } else if (listeIntersections.contains(inter)) {
            System.out.println("Intersection " + inter.getNom() + " déjà présente, ignorée");
        } else {
            System.out.println("Limite de taille du trajet atteinte");
        }
    }
    
    public int getTempsTotal() {
        return tempsTotal;
    }
    
    public void setTempsTotal(int tempsTotal) {
        if (tempsTotal < 0) {
            throw new IllegalArgumentException("Le temps total de trajet ne peut pas être négatif");
        }
        this.tempsTotal = tempsTotal;
    }
    
    public List<Intersection> getListeIntersections() {
        return new ArrayList<>(listeIntersections); // Retourner une copie pour éviter les modifications externes
    }
    
    public int getNombreIntersections() {
        return listeIntersections.size();
    }
    
    /**
     * Affiche le trajet dans la console (liste des intersections et temps total).
     */
    public void imprimerTrajet() {
        if (listeIntersections.isEmpty()) {
            System.out.println("Le trajet est vide.");
            return;
        }
        System.out.println("Trajet optimal :");
        for (Intersection inter : listeIntersections) {
            System.out.println("  - " + inter.getNom() + " (id=" + inter.getId() + ")");
        }
        System.out.println("Durée totale du trajet : " + tempsTotal + " unités de temps");
    }
}