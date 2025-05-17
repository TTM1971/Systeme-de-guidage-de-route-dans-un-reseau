package labo;

import java.util.*;

public class GPS {
    private CarteVille carteVille;
    private List<Evenement> evenementsActifs = new ArrayList<>();
    
    public GPS() {
        this.carteVille = new CarteVille();
    }
    
    public void chargerReseauDepuisFichier(String filePath) {
        carteVille.chargerReseauDepuisFichier(filePath);
    }
    
    // --- Gestion des événements ---
    public void declarerEvenement(Evenement evenement) {
        evenementsActifs.add(evenement);
        evenement.appliquerEffet(this);
    }
    
    public void resoudreEvenement(Intersection intersection) {
        evenementsActifs.removeIf(e -> e.getIntersection().equals(intersection));
        reinitialiserPoids();
        recalculerRoutesAffectees(intersection);
    }
    
    public List<Evenement> getEvenementsActifs() {
        return Collections.unmodifiableList(evenementsActifs);
    }
    
    public void supprimerTousEvenements() {
        evenementsActifs.clear();
    }
    
    private void recalculerRoutesAffectees(Intersection intersection) {
        for (Route route : intersection.getRoutes()) {
            route.reinitialiserPoids();
        }
        for (Evenement evt : evenementsActifs) {
            if (evt.getIntersection().getRoutes().stream()
                .anyMatch(r -> r.relie(evt.getIntersection(), intersection))) {
                evt.appliquerEffet(this);
            }
        }
    }
    
    // --- Calcul d'itinéraire ---
    public Trajet calculerCheminOptimal(int idDepart, int idArrivee) {
        if (!carteVille.contientIntersection(idDepart) || !carteVille.contientIntersection(idArrivee)) {
            System.out.println("Intersection de départ ou d'arrivée invalide");
            return null;
        }

        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> predecesseurs = new HashMap<>();
        Set<Integer> visite = new HashSet<>();
        PriorityQueue<Node> filePriorite = new PriorityQueue<>(Comparator.comparingInt(node -> node.distance));

        // Initialisation
        for (Integer id : carteVille.getIntersections().keySet()) {
            distances.put(id, Integer.MAX_VALUE);
            predecesseurs.put(id, null);
        }
        distances.put(idDepart, 0);
        filePriorite.add(new Node(idDepart, 0));

        // Limite pour éviter les boucles infinies
        int maxIterations = carteVille.getIntersections().size() * 100;
        int iteration = 0;

        // Algorithme Dijkstra
        while (!filePriorite.isEmpty() && iteration++ < maxIterations) {
            Node courant = filePriorite.poll();
            int currentId = courant.id;

            if (visite.contains(currentId)) {
                continue;
            }
            visite.add(currentId);

            if (currentId == idArrivee) {
                break;
            }

            Intersection current = carteVille.getIntersection(currentId);
            for (Route route : current.getRoutes()) {
                Intersection voisin = route.getAutreIntersection(current);
                int idVoisin = voisin.getId();

                if (visite.contains(idVoisin)) {
                    continue;
                }

                int nouvelleDist = distances.get(currentId) + route.getWeight();
                if (nouvelleDist < distances.get(idVoisin)) {
                    distances.put(idVoisin, nouvelleDist);
                    predecesseurs.put(idVoisin, currentId);
                    filePriorite.add(new Node(idVoisin, nouvelleDist));
                }
            }
        }

        if (iteration >= maxIterations) {
            System.out.println("Limite d'itérations atteinte dans calculerCheminOptimal");
            return null;
        }

        // Vérification si un chemin existe
        if (distances.get(idArrivee) == Integer.MAX_VALUE || predecesseurs.get(idArrivee) == null && idDepart != idArrivee) {
            System.out.println("Aucun chemin trouvé de " + idDepart + " à " + idArrivee);
            return null;
        }

        // Construction du trajet
        Trajet trajet = new Trajet();
        List<Integer> chemin = new ArrayList<>();
        Integer courant = idArrivee;
        int maxPathLength = carteVille.getIntersections().size() + 1; // Limite pour éviter les cycles

        while (courant != null && chemin.size() < maxPathLength) {
            chemin.add(courant);
            courant = predecesseurs.get(courant);
            if (courant != null && chemin.contains(courant)) {
                System.out.println("Cycle détecté dans le chemin à l'intersection " + courant);
                return null;
            }
        }

        if (courant == null && chemin.get(chemin.size() - 1) != idDepart) {
            System.out.println("Impossible de remonter jusqu'à l'intersection de départ");
            return null;
        }

        Collections.reverse(chemin);
        for (int id : chemin) {
            trajet.ajouterIntersection(carteVille.getIntersection(id));
        }
        trajet.setTempsTotal(distances.get(idArrivee));

        System.out.println("Trajet calculé : " + trajet.getListeIntersections());
        return trajet;
    }
    
    public Trajet calculerCheminOptimal(String nomDepart, String nomArrivee) {
        Intersection depart = getIntersectionParNom(nomDepart);
        Intersection arrivee = getIntersectionParNom(nomArrivee);
        return (depart != null && arrivee != null) 
            ? calculerCheminOptimal(depart.getId(), arrivee.getId())
            : null;
    }
    
    public Trajet recalculerTrajet(Intersection positionActuelle, Intersection destination) {
        Objects.requireNonNull(positionActuelle, "La position actuelle ne peut pas être null");
        Objects.requireNonNull(destination, "La destination ne peut pas être null");
        
        // Réinitialiser les poids avant de recalculer
        this.reinitialiserPoids();
        
        // Appliquer les effets des événements actifs
        for (Evenement evt : evenementsActifs) {
            evt.appliquerEffet(this);
        }
        
        // Calculer le nouveau trajet
        return calculerCheminOptimal(positionActuelle.getId(), destination.getId());
    }
    
    // --- Méthodes utilitaires ---
    public Intersection getIntersectionParNom(String nom) {
        return carteVille.getIntersectionParNom(nom);
    }
    
    public Route getRouteBetween(int id1, int id2) {
        return carteVille.getRouteEntre(id1, id2);
    }
    
    public void reinitialiserPoids() {
        carteVille.getRoutes().forEach(Route::reinitialiserPoids);
    }
    
    public CarteVille getCarteVille() {
        return carteVille;
    }
    
    private static class Node {
        int id;
        int distance;

        Node(int id, int distance) {
            this.id = id;
            this.distance = distance;
        }
    }
}