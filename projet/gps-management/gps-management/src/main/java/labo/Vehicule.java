package labo;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Vehicule {
    private Circle forme;

    public Vehicule(double x, double y) {
        forme = new Circle(x, y, 5, Color.YELLOW);
    }

    // Pour l'animation
    public Node getNode() {
        return forme;
    }

    // Alternative pour plus de clarté dans TrajetAnimator
    public Circle getForme() {
        return forme;
    }

    // Déplacement instantané (sans animation)
    public void deplacerInstant(double x, double y) {
        forme.setTranslateX(0);
        forme.setTranslateY(0);
        forme.setCenterX(x);
        forme.setCenterY(y);
    }

    // Méthode pour ajouter sur la scène
    public Circle getCircle() {
        return forme;
    }
}
