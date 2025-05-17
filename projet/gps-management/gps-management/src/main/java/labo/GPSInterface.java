package labo;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import java.util.*;

public class GPSInterface extends Application {
    private GPS gps;
    private Group root;
    private Vehicule vehicule;
    private TrajetAnimator animateur;
    private Intersection depart, arrivee;
    private final List<Node> elementsTrajet = new ArrayList<>();

    // Composants UI
    private Label infoLabel = new Label();
    private TextField departField = new TextField();
    private TextField arriveeField = new TextField();
    private TextField eventField = new TextField();
    private Slider congestionSlider = new Slider(0, 2, 0);

    @Override
    public void start(Stage primaryStage) {
        gps = new GPS();
        gps.chargerReseauDepuisFichier("reseau.txt");

        root = new Group();
        VBox mainLayout = new VBox(10);
        Scene scene = new Scene(mainLayout, 1000, 700);

        configurerUI(mainLayout);
        dessinerReseau();

        primaryStage.setTitle("Système de Navigation");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void configurerUI(VBox mainLayout) {
        HBox navigationBox = new HBox(10, 
            new Label("Départ:"), departField,
            new Label("Arrivée:"), arriveeField,
            new Button("Calculer trajet") {{
                setOnAction(e -> calculerTrajet());
            }}
        );
        
        HBox eventBox = new HBox(10,
            new Label("Intersection événement:"), eventField,
            new Button("Simuler accident") {{
                setOnAction(e -> simulerAccident());
            }},
            new Label("Niveau congestion (0-2):"), congestionSlider,
            new Button("Simuler congestion") {{
                setOnAction(e -> simulerCongestion());
            }},
            new Button("Réinitialiser") {{
                setOnAction(e -> resetReseau());
                setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
            }}
        );

        mainLayout.getChildren().addAll(
            new Label("SYSTÈME DE NAVIGATION INTELLIGENT") {{
                setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            }},
            navigationBox,
            eventBox,
            infoLabel,
            new ScrollPane(root) {{
                setFitToWidth(true);
                setFitToHeight(true);
            }}
        );
    }
    
    private void dessinerReseau() {
        root.getChildren().clear();
        
        for (Route route : gps.getCarteVille().getRoutes()) {
            Line line = new Line(
                route.getA().getX(), route.getA().getY(),
                route.getB().getX(), route.getB().getY()
            );
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(2);
            route.setLine(line);
            root.getChildren().add(line);
        }
        
        for (Intersection inter : gps.getCarteVille().getIntersections().values()) {
            Circle circle = new Circle(inter.getX(), inter.getY(), 5, Color.BLUE);
            Text label = new Text(inter.getX() + 8, inter.getY() - 8, inter.getNom());
            root.getChildren().addAll(circle, label);
        }
    }

    private void calculerTrajet() {
        effacerAncienTrajet();
        
        depart = gps.getIntersectionParNom(departField.getText().trim());
        arrivee = gps.getIntersectionParNom(arriveeField.getText().trim());
        
        if (depart == null || arrivee == null) {
            afficherAlerte("Points de départ/destination invalides");
            return;
        }
        
        Trajet trajet = null;
        try {
            trajet = gps.calculerCheminOptimal(depart.getId(), arrivee.getId());
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            afficherAlerte("Erreur de mémoire lors du calcul du trajet");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors du calcul du trajet : " + e.getMessage());
            return;
        }
        
        if (trajet == null) {
            afficherAlerte("Aucun chemin trouvé");
            return;
        }
        
        System.out.println("Trajet initial : " + trajet.getListeIntersections());
        
        vehicule = new Vehicule(depart.getX(), depart.getY());
        root.getChildren().add(vehicule.getNode());
        
        mettreItineraireEnRouge(trajet);
        
        animateur = new TrajetAnimator(
            trajet, vehicule, gps, arrivee,
            this::recalculerTrajetDepuisPositionActuelle
        );
        animateur.animer();
    }

    private void resetReseau() {
        if (animateur != null) {
            animateur.arreter();
            animateur = null;
        }

        gps.reinitialiserPoids();
        gps.supprimerTousEvenements();

        effacerAncienTrajet();
        dessinerReseau();

        vehicule = null;
        depart = null;
        arrivee = null;

        afficherAlerte("Réseau réinitialisé !");
    }
    
    private void simulerAccident() {
        if (animateur == null || vehicule == null) {
            afficherAlerte("Aucun trajet en cours !");
            return;
        }

        Intersection intersection = gps.getIntersectionParNom(eventField.getText().trim());
        if (intersection == null) {
            afficherAlerte("Intersection d'accident invalide");
            return;
        }

        gps.declarerEvenement(new Accident(intersection));
        
        for (Route route : intersection.getRoutes()) {
            route.getLine().setStroke(Color.ORANGERED);
            route.getLine().setStrokeWidth(4);
        }

        afficherAlerte("Accident simulé à " + intersection.getNom());
        recalculerTrajetDepuisPositionActuelle();
    }

    private void simulerCongestion() {
        if (animateur == null || vehicule == null) {
            afficherAlerte("Aucun trajet en cours !");
            return;
        }

        Intersection inter = gps.getIntersectionParNom(eventField.getText().trim());
        if (inter == null) {
            afficherAlerte("Intersection de congestion invalide");
            return;
        }
        
        int niveau = (int) congestionSlider.getValue();
        gps.declarerEvenement(new Congestion(inter, niveau));
        
        for (Route route : inter.getRoutes()) {
            route.getLine().setStroke(Color.PURPLE);
            route.getLine().setStrokeWidth(3);
        }
        
        afficherAlerte("Congestion niveau " + niveau + " à " + inter.getNom());
        recalculerTrajetDepuisPositionActuelle();
    }

    private void recalculerTrajetDepuisPositionActuelle() {
        if (animateur == null || arrivee == null || vehicule == null) {
            afficherAlerte("Impossible de recalculer : trajet non initialisé");
            return;
        }

        double x = vehicule.getForme().getCenterX();
        double y = vehicule.getForme().getCenterY();
        Intersection positionActuelle = trouverIntersectionProche(x, y);
        
        if (positionActuelle == null) {
            afficherAlerte("Aucune intersection proche trouvée");
            return;
        }

        System.out.println("Recalcul depuis : " + positionActuelle.getNom());

        animateur.arreter();
        animateur = null;

        javafx.application.Platform.runLater(() -> {
            try {
                Trajet nouveauTrajet = gps.recalculerTrajet(positionActuelle, arrivee);
                if (nouveauTrajet == null) {
                    afficherAlerte("Aucun trajet alternatif trouvé");
                    return;
                }

                System.out.println("Nouveau trajet : " + nouveauTrajet.getListeIntersections());

                effacerAncienTrajet();
                vehicule.deplacerInstant(positionActuelle.getX(), positionActuelle.getY());

                if (!root.getChildren().contains(vehicule.getNode())) {
                    root.getChildren().add(vehicule.getNode());
                }

                mettreItineraireEnRouge(nouveauTrajet);

                animateur = new TrajetAnimator(
                    nouveauTrajet,
                    vehicule,
                    gps,
                    arrivee,
                    this::recalculerTrajetDepuisPositionActuelle
                );
                animateur.animer();
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                afficherAlerte("Erreur de mémoire lors du recalcul du trajet");
            } catch (Exception e) {
                e.printStackTrace();
                afficherAlerte("Erreur lors du recalcul : " + e.getMessage());
            }
        });
    }

    private Intersection trouverIntersectionProche(double x, double y) {
        for (Intersection inter : gps.getCarteVille().getIntersections().values()) {
            if (Math.abs(inter.getX() - x) < 10 && Math.abs(inter.getY() - y) < 10) {
                return inter;
            }
        }
        return null;
    }

    private void mettreItineraireEnRouge(Trajet trajet) {
        List<Intersection> intersections = trajet.getListeIntersections();
        double totalPoids = 0;
        
        for (int i = 0; i < intersections.size() - 1; i++) {
            Route r = gps.getRouteBetween(
                intersections.get(i).getId(), 
                intersections.get(i+1).getId()
            );
            
            if (r != null) {
                r.getLine().setStroke(Color.RED);
                r.getLine().setStrokeWidth(3);
                totalPoids += r.getWeight();
                
                afficherDirection(
                    intersections.get(i).getX(), intersections.get(i).getY(),
                    intersections.get(i+1).getX(), intersections.get(i+1).getY()
                );
            }
        }
        
        infoLabel.setText(String.format(
            "Trajet: %s → %s | Distance: %.1f | Temps: %.1f",
            depart != null ? depart.getNom() : "Inconnu",
            arrivee != null ? arrivee.getNom() : "Inconnu",
            totalPoids,
            (double)trajet.getTempsTotal()
        ));
    }

    private void afficherDirection(double x1, double y1, double x2, double y2) {
        Line line = new Line(x1, y1, x2, y2);
        line.setStroke(Color.RED);
        line.setStrokeWidth(1);
        
        Polygon arrow = new Polygon();
        arrow.getPoints().addAll(
            x2, y2,
            x2 - 10, y2 - 5,
            x2 - 10, y2 + 5
        );
        arrow.setFill(Color.RED);
        
        elementsTrajet.add(line);
        elementsTrajet.add(arrow);
        root.getChildren().addAll(line, arrow);
    }

    private void effacerAncienTrajet() {
        root.getChildren().removeAll(elementsTrajet);
        elementsTrajet.clear();
        
        for (Route route : gps.getCarteVille().getRoutes()) {
            route.getLine().setStroke(Color.BLACK);
            route.getLine().setStrokeWidth(2);
        }
    }

    private void afficherAlerte(String message) {
        infoLabel.setTextFill(Color.RED);
        infoLabel.setText(message);
        
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(e -> infoLabel.setText(""));
        pause.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}