package labo;

import java.util.List;
import java.util.ArrayList;

public class Intersection {
    private String nom;
    private int id;
    private int x;
    private int y;
    private List<Route> routes; // Routes adjacentes (voisins)
    
    /**
     * Constructeur d'une intersection sans coordonnées (coordonnées définies plus tard).
     * @param nom Nom de l'intersection
     * @param id  Identifiant unique de l'intersection
     */
    public Intersection(String nom, int id) {
        if (id < 0) {
            throw new IllegalArgumentException("L'identifiant de l'intersection ne peut pas être négatif");
        }
        this.nom = nom;
        this.id = id;
        this.x = 0;
        this.y = 0;
        this.routes = new ArrayList<>();
    }
    
    /**
     * Constructeur d'une intersection avec coordonnées initiales.
     * @param nom Nom de l'intersection
     * @param id  Identifiant unique
     * @param x   Coordonnée X (horizontale)
     * @param y   Coordonnée Y (verticale)
     */
    public Intersection(String nom, int id, int x, int y) {
        if (id < 0) {
            throw new IllegalArgumentException("L'identifiant de l'intersection ne peut pas être négatif");
        }
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Les coordonnées doivent être positives ou nulles");
        }
        this.nom = nom;
        this.id = id;
        this.x = x;
        this.y = y;
        this.routes = new ArrayList<>();
    }
    
    public String getNom() {
        return nom;
    }
    
    public int getId() {
        return id;
    }
    
    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        if (x < 0) throw new IllegalArgumentException("La coordonnée X doit être positive");
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        if (y < 0) throw new IllegalArgumentException("La coordonnée Y doit être positive");
        this.y = y;
    }
    
    public List<Route> getRoutes() {
        return routes;
    }
    
    /**
     * Ajoute une route adjacente à cette intersection.
     * @param route Route à ajouter
     */
    public void ajouterRoute(Route route) {
        if (route == null) throw new IllegalArgumentException("La route adjacente ne peut pas être null");
        routes.add(route);
    }
    
    @Override
    public String toString() {
        return "Intersection " + nom + " (id=" + id + ", x=" + x + ", y=" + y + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Intersection other = (Intersection) obj;
        return this.id == other.id && this.nom.equals(other.nom);
    }
}
