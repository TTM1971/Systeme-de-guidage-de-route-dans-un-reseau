package labo;

import javafx.animation.*;
import javafx.scene.shape.*;
import javafx.util.Duration;
import java.util.List;

public class TrajetAnimator {
    private Trajet trajet;
    private final Vehicule vehicule;
    private final GPS gps;
    private final Intersection destination;
    private final Runnable onRecalculRequired;
    private final SequentialTransition sequence = new SequentialTransition();
    
    public TrajetAnimator(Trajet trajet, Vehicule vehicule, GPS gps, 
                         Intersection destination, Runnable onRecalculRequired) {
        this.trajet = trajet;
        this.vehicule = vehicule;
        this.gps = gps;
        this.destination = destination;
        this.onRecalculRequired = onRecalculRequired;
        
        // Configuration de la séquence
        sequence.setOnFinished(e -> {
            if (!sequence.getStatus().equals(Animation.Status.STOPPED)) {
                vehicule.deplacerInstant(
                    trajet.getListeIntersections().get(trajet.getListeIntersections().size() - 1).getX(),
                    trajet.getListeIntersections().get(trajet.getListeIntersections().size() - 1).getY()
                );
            }
        });
    }

    public void animer() {
        sequence.getChildren().clear();
        List<Intersection> parcours = trajet.getListeIntersections();

        for (int i = 0; i < parcours.size() - 1; i++) {
            Intersection depart = parcours.get(i);
            Intersection arrivee = parcours.get(i + 1);
            sequence.getChildren().add(creerTransition(depart, arrivee));
        }

        sequence.play();
    }

    private PathTransition creerTransition(Intersection depart, Intersection arrivee) {
        Path chemin = new Path(
            new MoveTo(depart.getX(), depart.getY()),
            new LineTo(arrivee.getX(), arrivee.getY())
        );

        PathTransition transition = new PathTransition(
            Duration.seconds(2), chemin, vehicule.getNode());
        
        transition.setOnFinished(e -> {
            if (sequence.getStatus().equals(Animation.Status.STOPPED)) return;
            
            vehicule.deplacerInstant(arrivee.getX(), arrivee.getY());
            verifierEvenements(arrivee);
        });

        return transition;
    }

    private void verifierEvenements(Intersection positionActuelle) {
        // Nouveau : vérifier d'abord si l'animation est déjà stoppée
        if (sequence.getStatus() == Animation.Status.STOPPED) return;

        gps.getEvenementsActifs().stream()
            .filter(evt -> evt.getIntersection().equals(positionActuelle))
            .findFirst()
            .ifPresent(evt -> {
                // 1. Stopper proprement l'animation
                sequence.stop();
                
                // 2. Appliquer l'effet de l'événement
                evt.appliquerEffet(gps);
                
                // 3. Important : relancer le recalcul dans le thread FX
                javafx.application.Platform.runLater(() -> {
                    onRecalculRequired.run();
                });
            });
    }
    
    /*private void verifierEvenements(Intersection positionActuelle) {
        gps.getEvenementsActifs().stream()
            .filter(evt -> evt.getIntersection().equals(positionActuelle))
            .findFirst()
            .ifPresent(evt -> {
                // 1. Appliquer l'effet
                evt.appliquerEffet(gps);
                
                // 2. Arrêter proprement
                sequence.stop();
                
                // 3. Notifier le besoin de recalcul
                onRecalculRequired.run();
            });
    }*/

    public void reinitialiser() {
        sequence.stop();
        sequence.getChildren().clear();
    }
    
    public void arreter() {
        sequence.stop();
    }

    public void mettreAJourTrajet(Trajet nouveauTrajet) {
        this.trajet = nouveauTrajet;
    }
    
    public Trajet getTrajet() {
        return this.trajet;
    }
}