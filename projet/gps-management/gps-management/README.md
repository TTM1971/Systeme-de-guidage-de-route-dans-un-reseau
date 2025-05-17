# GPS Management

**GPS Management**

Bienvenue dans **GPS Management**, une application JavaFX qui simule un système de navigation intelligent. L'application permet de calculer des trajets optimaux entre deux points d'un réseau routier, de simuler des événements comme des accidents ou des congestions, et de réajuster les trajets en temps réel.

Ce document explique comment exécuter l'application et comment utiliser son interface graphique.

## Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants :

- **Java 21** : Installez JDK 21 (ex. Eclipse Temurin ou Liberica JDK 21 Full).
    
    - Téléchargez depuis Adoptium ou BellSoft.
        
    - Vérifiez avec :
        
        ```cmd
        java -version
        ```
        
        Résultat attendu :
        
        ```
        openjdk version "21.x" ...
        ```
        
- **Maven** : Installez Maven pour gérer les dépendances et compiler le projet.
    
    - Si vous n'avez pas Maven dans votre système d'exploitation, suivez les instructions ci-dessous :
        
        1. **Trouver le chemin du répertoire Maven**
            - Après avoir téléchargé Maven, décompressez l'archive si elle est au format `.zip` ou `.tar.gz`.
            - Notez le chemin complet vers le dossier racine de Maven (ex. : `C:\Programmes\apache-maven-3.x.x`)
        2. **Ajouter Maven dans les variables d'environnement**
            - **Sous Windows** :
                
                1. **Ouvrir les paramètres des variables d'environnement** :
                    - Faites un clic droit sur "Ce PC" ou "Ordinateur" > **Propriétés** > **Paramètres système avancés**.
                    - Cliquez sur le bouton **Variables d'environnement**.
                2. **Créer une nouvelle variable système pour Maven** :
                    - Sous "Variables système", cliquez sur **Nouvelle**.
                    - Renseignez les champs suivants :
                        - **Nom de la variable** : `MAVEN_HOME`.
                        - **Valeur de la variable** : Chemin vers le dossier Maven (exemple : `C:\apache-maven-3.x.x`).
                3. **Modifier la variable PATH** :
                    - Trouvez la variable système `PATH` dans la liste et cliquez sur **Modifier**.
                    - Ajoutez à la fin : `%MAVEN_HOME%\bin` (utilisez un point-virgule `;` pour séparer les chemins existants si nécessaire).
                4. **Valider** :
                    - Cliquez sur **OK** pour enregistrer les modifications.
                    - Redémarrez le système ou l'IDE pour appliquer les changements.
                
                - b. **Sous Linux/macOS** :
                    1. **Éditer le fichier de configuration du shell** :
                        
                        - Ouvrez le fichier de configuration correspondant à votre shell :
                            
                            - Pour bash : `~/.bashrc`.
                                
                            - Pour zsh : `~/.zshrc`.
                                
                            - Pour fish : `~/.config/fish/config.fish`.
                                
                    2. **Ajouter les variables MAVEN_HOME et PATH** :
                        
                        - Ajoutez les lignes suivantes :
                            
```
export MAVEN_HOME=/chemin/vers/apache-maven 
export PATH=$PATH:$MAVEN_HOME/bin
```
                            
                    3. **Appliquer les changements** :
                        
                        - Rechargez la configuration avec :
                            
                            ```
                            source ~/.bashrc
                            ```
                            
                        - Ou redémarrez le terminal.
                            
        3. **Tester l'installation**
        
        - Ouvrez un terminal ou l'invite de commande.
            
        - Tapez :
            
            ```
            mvn -v
            ```
            
        - Cela doit afficher la version de Maven ainsi que l'environnement Java configuré.
            

## Exécution du programme

Pour exécuter l'application, suivez ces étapes dans le répertoire du projet (`gps-management`) :

1. **Compiler le projet** :
    
    ```cmd
    mvn compile
    ```
    
    Cela compile les fichiers sources (`GPSInterface.java`, `GPS.java`, `CarteVille.java`, etc.) et télécharge les dépendances JavaFX 21 définies dans `pom.xml`.
    
2. **Lancer l'application** :
    
    ```cmd
    mvn javafx:run
    ```
    
    Cela exécute l'application JavaFX en utilisant le plugin `javafx-maven-plugin`, qui configure automatiquement les modules JavaFX (`javafx.controls`, `javafx.fxml`, `javafx.graphics`). L'interface graphique s'ouvrira.
    
3. **Vérification** :
    
    - Assurez-vous que `reseau.txt` est dans le dossier racine.
    - Si l'interface ne s'ouvre pas, vérifiez les erreurs dans la console et consultez la section "Dépannage" ci-dessous.

### Alternative : Exécuter le JAR

Pour exécuter le JAR généré (`target\gps-management-1.0.jar`) directement :

1. Générez le JAR :
    
    ```cmd
    mvn clean package
    ```
    
2. Exécutez le JAR :
    
    - Si vous utilisez un JDK sans JavaFX (ex. Eclipse Temurin) :
        
        ```cmd
        java --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar target\gps-management-1.0.jar
        ```
        
    - Si vous utilisez Liberica JDK 21 Full (avec JavaFX inclus) :
        
        ```cmd
        java -jar target\gps-management-1.0.jar
        ```
        

### Dépannage

- **Erreur** : `JavaFX runtime components are missing`
    
    - Vérifiez si JavaFX est inclus :
        
        ```cmd
        java --list-modules | findstr javafx
        ```
        
    - Si absent, utilisez `--add-modules` ou installez Liberica JDK 21 Full.
        
- **Erreur de compilation** :
    
    - Exécutez :
        
        ```cmd
        mvn clean package -e -X
        ```
        
        Vérifiez les erreurs dans la sortie.
        
- `reseau.txt` **introuvable ou mal formaté** :
    
    - Confirmez sa présence :
        
        ```cmd
        dir reseau.txt
        ```
        
    - Vérifiez son format (voir exemple ci-dessus).
        

## Fonctionnement de l'interface

L'interface graphique, implémentée dans `GPSInterface.java`, permet de visualiser un réseau routier, de calculer des trajets optimaux, et de simuler des événements affectant la navigation. Voici comment utiliser chaque fonctionnalité :

### 1. Vue générale

- **Titre** : "SYSTÈME DE NAVIGATION INTELLIGENT".
- **Réseau routier** : Affiché comme un graphe avec :
    - **Intersections** : Cercles bleus avec noms (ex. "A", "X").
    - **Routes** : Lignes noires reliant les intersections.
- **Panneau de contrôle** : Contient des champs et boutons pour interagir.
- **Étiquette d'information** : Affiche les messages d'erreur ou les détails du trajet (en bas).

### 2. Calculer un trajet

- **Champs** :
    - **Départ** : Entrez le nom d'une intersection (ex. "A").
    - **Arrivée** : Entrez le nom d'une autre intersection (ex. "X").
- **Bouton "Calculer trajet"** :
    - Cliquez pour calculer le chemin optimal entre les intersections saisies.
    - Le trajet s'affiche en **rouge** sur le graphe, avec des flèches indiquant la direction.
    - Un véhicule (point mobile) suit le trajet.
    - L'étiquette d'information affiche :
        - Points de départ et d'arrivée.
        - Distance totale.
        - Temps estimé.
- **Comportement** :
    - Si les intersections sont invalides ou aucun chemin n'existe, un message d'erreur s'affiche (en **rouge**, disparaît après 3 secondes).
    - En cas d'erreur de mémoire (`OutOfMemoryError`), un message indique un problème de calcul.

### 3. Simuler un accident

- **Champ "Intersection événement"** :
    - Entrez le nom d'une intersection (ex. "U").
- **Bouton "Simuler accident"** :
    - Simule un accident à l'intersection spécifiée.
    - Les routes connectées à cette intersection deviennent **orange-rouge** (épaisseur accrue).
    - Le trajet est recalculé automatiquement depuis la position actuelle du véhicule pour contourner l'accident.
- **Comportement** :
    - Nécessite un trajet en cours (sinon, erreur : "Aucun trajet en cours !").
    - Si l'intersection est invalide, un message d'erreur s'affiche.
    - Le nouveau trajet s'affiche en **rouge**, et le véhicule suit le chemin mis à jour.

### 4. Simuler une congestion

- **Champ "Intersection événement"** :
    - Entrez le nom d'une intersection (ex. "O").
- **Curseur "Niveau congestion (0-2)"** :
    - Sélectionnez un niveau de congestion (0 = faible, 1 = moyen, 2 = fort).
- **Bouton "Simuler congestion"** :
    - Simule une congestion à l'intersection spécifiée avec le niveau choisi.
    - Les routes connectées deviennent **violet** (épaisseur accrue).
    - Le trajet est recalculé pour éviter la congestion.
- **Comportement** :
    - Nécessite un trajet en cours.
    - Si l'intersection est invalide, un message d'erreur s'affiche.
    - Le nouveau trajet s'affiche, et le véhicule suit le chemin mis à jour.

### 5. Réinitialiser

- **Bouton "Réinitialiser" (rouge)** :
    - Réinitialise le réseau routier à son état initial.
    - Supprime tous les événements (accidents, congestions).
    - Efface le trajet affiché et le véhicule.
    - Restaure les routes en **noir** (épaisseur normale).
    - Message de confirmation : "Réseau réinitialisé !".
- **Utilité** :
    - Permet de repartir de zéro pour un nouveau trajet.

## Conseils d'utilisation

- **Noms d'intersections** : Utilisez les noms exacts définis dans `reseau.txt` (ex. "A", "B", sensibles à la casse).
    
- **Performance** : Pour un grand `reseau.txt`, augmentez la mémoire Java si nécessaire :
    
    ```cmd
    java -Xmx2g --add-modules javafx.controls,javafx.fxml,javafx.graphics -jar target\gps-management-1.0.jar
    ```
    
- **Tests** : Les tests sont désactivés (`<skipTests>true</skipTests>`). Pour les réactiver, modifiez `pom.xml` et ajoutez des tests dans `src/test/java`.
    

## Conclusion

**GPS Management** est une application intuitive pour simuler la navigation routière avec gestion d'événements dynamiques. Suivez les instructions d'exécution pour lancer l'application, et utilisez l'interface pour explorer les trajets et tester les scénarios d'accidents ou de congestions. Pour toute question, consultez les sections de dépannage ou contactez le développeur.