package labo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


import labo.Intersection;
import labo.Route;

public class CarteVille {
    private Map<Integer, Intersection> intersections;
    private Map<String, Intersection> intersectionsParNom;
    private List<Route> routes;

    public CarteVille() {
        this.intersections = new HashMap<>();
        this.intersectionsParNom = new HashMap<>();
        this.routes = new ArrayList<>();
    }

    /**
     * Charge le réseau routier depuis un fichier texte.
     * Le fichier doit décrire les intersections, leurs coordonnées, puis les routes et durées.
     * @param filePath Chemin du fichier à lire
    */
    public void chargerReseauDepuisFichier(String filePath) {
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            
            if (!scanner.hasNextLine()) {
                scanner.close();
                return;
            }
            // Lire la première ligne contenant le nombre d'intersections et de routes (pas strictement nécessaire)
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                scanner.close();
                return;
            }
            
            // Lecture des intersections (jusqu'à la ligne contenant "$")
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("$")) {
                    break; // fin de la liste des intersections
                }
                if (line.trim().isEmpty()) continue;
                // Chaque ligne d'intersection : "<id> <nom>"
                // On sépare par le premier espace pour isoler l'ID et le nom
                String[] parts = line.split(" ", 2);
                int id = Integer.parseInt(parts[0].trim());
                String nom = parts[1].trim();
                // Créer l'intersection sans coordonnées pour l'instant
                Intersection inter = new Intersection(nom, id);
                intersections.put(id, inter);
                intersectionsParNom.put(nom, inter);
            }
            
            // Lecture des coordonnées de chaque intersection (jusqu'à "$")
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("$")) {
                    break; // fin des coordonnées
                }
                if (line.trim().isEmpty()) continue;
                // Chaque ligne de coordonnées : "<id> <x> <y>"
                String[] parts = line.split(" ");
                int id = Integer.parseInt(parts[0]);
                int posX = Integer.parseInt(parts[1]);
                int posY = Integer.parseInt(parts[2]);
                Intersection inter = intersections.get(id);
                if (inter != null) {
                    // Mettre à jour les coordonnées de l'intersection
                    inter.setX(posX);
                    inter.setY(posY);
                }
            }
            
            // Lecture des routes (jusqu'à "$" ou fin du fichier)
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (line.equals("$")) {
                    break; // fin de la liste des routes
                }
                if (line.trim().isEmpty()) continue;
                // Chaque ligne de route : "<id_depart> <id_arrivee> <poids>"
                String[] parts = line.split(" ");
                int idDepart = Integer.parseInt(parts[0]);
                int idArrivee = Integer.parseInt(parts[1]);
                int temps = Integer.parseInt(parts[2]);
                Intersection interDep = intersections.get(idDepart);
                Intersection interArr = intersections.get(idArrivee);
                if (interDep == null || interArr == null) {
                    continue; // ignorer si l'une des intersections n'existe pas (données invalides)
                }
                // Créer la route (on suppose le graphe non orienté, donc route bidirectionnelle)
                Route route = new Route(interDep, interArr, temps);
                // Ajouter la route aux listes d'adjacence des deux intersections
                interDep.ajouterRoute(route);
                interArr.ajouterRoute(route);
                // Stocker la route dans la liste globale
                routes.add(route);
            }
            
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Le fichier " + filePath + " est introuvable.");
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement du réseau : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retourne une intersection par son nom.
     * @param nom Nom de l'intersection recherchée
     * @return L'objet Intersection correspondant, ou null si non trouvé
     */
    public Intersection getIntersectionParNom(String nom) {
        return intersectionsParNom.get(nom);
    }
    
    public Intersection getIntersection(int id) {
        return intersections.get(id);
    }
    
    public Route getRouteEntre(int id1, int id2) {
        Intersection i1 = intersections.get(id1);
        Intersection i2 = intersections.get(id2);
        
        if (i1 == null || i2 == null) return null;
        
        for (Route route : i1.getRoutes()) {
            if (route.relie(i1, i2)) {
                return route;
            }
        }
        return null;
    }
    
    /**
     * Retourne la liste de toutes les intersections du graphe.
     */
    
    public Map<Integer, Intersection> getIntersections() {
        return this.intersections;
    }

    public boolean contientIntersection(int id) {
        return this.intersections.containsKey(id);
    }
    
    /**
     * Retourne la liste de toutes les routes du graphe.
     */
    public List<Route> getRoutes() {
        return routes;
    }
   
}