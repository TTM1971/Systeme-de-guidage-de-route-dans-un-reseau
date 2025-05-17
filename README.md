 
Devoir 2 Programmation II 
 
Hiver 2025
 
Prof. Ilham Benyahia 
 
Application de la programmation par événements  
Un système de guidage de routes dans un réseau.  
 
 
I. Objectifs relativement au cours 
L’objectif principal du devoir 2 est de maitriser la programmation par événements via un système à base d’objets intégrant les concepts du cours. Les concepts objets doivent être considérés, appliqués et valider afin d<atteindre les objectifs de qualité  de développement du cours programmation II.    pour développer le système considéré dans ce devoir.  
La partie qualité nécessite l’application les connaissances du cours relativement à différente phases de développement.    
II. Contexte du devoir  
Nous considérons le contexte des systèmes des transports intelligents relativement aux systèmes de guidage. 
Parmi les fonctionnalités des systèmes de guidage, nous considérons la recherche du meilleur chemin en fonction de l’état du trafic et des accidents.  Dans ce contexte, les véhicules sont équipés par des GPS, des moyens de communications et des capacités de traitements d’informations.    Les GPS sont munis d’écrans dont une interface qui fournit aux conducteurs des informations sur le trafic ou des recommandations sur les prochaines directions à prendre à partir d’un moment particulier.  Par exemple lorsqu’il y a du trafic sur une artère qui devient congestionnée ou la présence d’un accident, le conducteur est informé par l’écran du GPS.   A la réception d’ informations, des recommandations sont affichées sur le GPS pour montrer  les meilleurs chemins à prendre à partir du moment présent.   
 
 
  
Figure 1 : Exemple d’une image GPS indiquant une direction  
Fonctionnement du système à développer et valider dans ce devoir  
Les concepts objets doivent être considérés et appliqués pour développer (analyse, conception et implémentation) le système de guidage décrit précédemment.   
Le fonctionnement de ce système se décrit comme suit :  Un réseau routier est fixé et les véhicules se déplacent à l’intérieur de ce réseau. Le réseau routier emprunté par le conducteur est défini par un ensemble d’artères et d’intersections.  Les événements qui arrivent sur le réseau routier peuvent être identifiés et visualisés sur le réseau routier.  
Chaque véhicule circule dans ce réseau reçoit des informations sur l’état de ce réseau.   Lors d’un déplacement entre les points A et B (sommets du réseau), le conducteur du véhicule concerné emprunte le plus court chemin.  Entre les points A et B, le conducteur sera informé régulièrement via l’écran de son GPS et dès qu’il y a un accident il lui sera communiqué. Les nouvelles directions à prendre s’il y a lieu seront affichées par le GPS (exemple, dans une intersection, s’il doit aller à droite, à gauche ou poursuivre en ligne droite selon la direction actuelle).  Le système de calcul du plus court chemin est activé au début du déplacement.  Il sera réactivé à chaque fois que les nouvelles informations du GPS affectent les résultats des calculs précédents. Les nouvelles directions doivent être basées sur les meilleurs chemins calculés.     
 
 
III. Travail à faire dans un environnement objet  
 
Phase 1 : Analyse et Spécifications: 20 pts 
 
 
Une analyse de spécifications consiste  à rédiger en quelques lignes le fonctionnement du système à développer.  La spécification prend aussi en considération  des composantes graphiques potentielles de la librairie jdk qui permettent de visualiser au mieux aux besoins de fonctionnement du système de guidage et son environnement.   La spécification consiste à présenter une étape qui mène vers la solution (conception) qu’on présente sous forme d’un graphe (avec ou sans un outil comme visio) pour rendre visible la description du système qui sera développé. Pour ce devoir, la spécification peut présenter des éléments des  interfaces qui  seront mieux présentées au niveau de la conception et qui permettent de faire des interactions en considérant  le réseau routier et l’écran du GPS ainsi que toute autre composante nécessaire pour l’initialisation et le fonctionnement de du système à développer.   
 
Pour ce devoir : Une analyse sera préposée (dans un document à  part), le travail de chaque équipe consiste à l’étudier relativement à  l’énoncé, établir des critères de qualité d’une spécification et procédé à  sa révision. Le résultat attendu est alors soit une validation ou une détection d’insuffisances ou erreur qu’il faut corriger pour produire un graphe et un paragraphe jugés conformes avec la description du travail à faire selon le contexte décrit. Les équipes peuvent aussi valider la proposition et l’adapter à leur propre vision (selon l’interface que chaque équipe souhaite développer dans ce devoir). Dans ce cas, une équipe valide et mentionne plutôt un travail d’adaptation. Il est fortement recommandé de rajouter un graphe (en visio ou autre) en plus du paragraphe explicatif. 
 
Phase 2 : Conception de la solution globale : 25 pts  
 
Mieux comprendre la conception : Une fois que le quoi, (ce qui est demandé de faire) est clair au niveau de la spécification, la phase suivante d’un développement est la conception qui présente comment procéder pour résoudre des problèmes et modéliser le système à développer.  Les étapes de modélisation discutées en cours sont à  considérer vu qu’il s’agit d’un développement basé sur le concept objet. 
Comme rappel sur l’exercice, il est important d’identifier les classes (incluant leurs attributs, méthodes).  Pour les méthodes qui ne sont pas simples, écrire des algorithmes fait partie de cette phase.   Établir des relations entre les classes s’avère nécessaire pour le développement d’un système impliquant plusieurs classes (elles doivent interagir).  Il est recommandé d’utiliser UML ou Visio pour présenter les classes et leurs relations.  
 
Parmi ces algorithmes de ce devoir, il faut expliquer la manière dont se fait la recherche du meilleur chemin. Il est possible d’écrire son propre algorithme ou considérer un algorithme existant pour l’appliquer ou le modifier s’il ne répond pas clairement aux besoins du systèmes à concevoir.  
 
Pour ce devoir : : Une proposition de quelques éléments de la conception sera   préposée (dans un document à  part), le travail de chaque équipe consiste à l’étudier relativement à  l’énoncé, établir des critères de qualité d’une conception et procédé à  sa révision. Le résultat attendu est alors soit une validation ou une détection d’insuffisances ou erreur qu’il faut corriger.  Dans tous les cas, une conception valide (même une extension ou correction de la proposition) doit être présentée clairement  pour  cette phase du devoir. 

Phase 3 : Codage de votre solution: 30 pts 
  
Codez la solution conçue incluant les classes des deux phases précédentes. On demande de considérer l’ensemble du matériel fournit en cours relativement à la qualité du code et particulièrement sa documentation (voir la section 11 : C112025 à lire et appliquer ce qui est pertinent à votre code).   
 
La réutilisation est permise (comme un algorithme existant et qu’il faut modifier au besoin).  Le plus important est de mettre une référence au code que vous réutilisez et adaptez tiut en expliquant ce que vous avez compris d’un code existant et comment vous l’avez modifié. 
  
Dans tous les cas, il faut considérer les particularités de l’énoncé et de votre spécification retenue. De manière similaire aux deux phases précédentes, un travail de révision est nécessaire.   
 
Phase 4 : Révision de votre travail et qualité du travail d’équipe :  25 pts  
 
1)	Fonctionnement du code :  Faire plusieurs jeux de tests pour faire varier les évènements de la route (congestion de tronçons et accidents). Montrer pour chaque jeu d’essai les interfaces associées (le réseau routier et la visualisation de l’interface du GPS).   Détaillez le fonctionnement de chaque test en décrivant la situation à partir du point de départ avec la destination associée et le ou les événements ainsi que les réactions du GPS.     
 
2)	Révision de votre code : En considérant les notes du cours relativement à la qualité, on demande de préparer une fiche de révision.  Procéder alors à l’analyse de votre travail (selon les critères vus en cours). Travaillez sur un tableau des analyses que vous préparez.  Montrez alors comment vous avez apporté des améliorations en commençant par la première révision.  
 
3)	Qualité du travail d’équipe : Il s’agit d’un travail d’équipes.  Chaque équipe comporte quatre étudiants) :  Les informations suivantes sont requises dans le rapport final. 1) Structure de l’équipe (avec ou sans un responsable qui gère le temps des réunions et livrables, etc), 2) organisation du travail, 3) modalité des partages du travail et des connaissances, etc.  
 
4)	Rédaction d’un rapport global : Rédiger les résultats de chaque phase dans un document Word bien structuré en adoptant les titres des phases du devoir et les tâches associées.  Respectez ls numérotations de l’énoncé au niveau de votre rapport. En plus de la rédaction de votre texte expliquant chaque phase, il faut intégrer des graphes (UML, Visio) et  images écran pour illustrer le fonctionnement de système.  
 
 
Livrables et modalités  
 
•	L’énoncé est disponible le 28 mars  
•	Date de remise du travail sur Moodle : le 25 avril (Aucun retard ne sera toléré, dernier jour de la session).  
•	Offre par la professeure de répondre aux questions sur la compréhension du travail à  faire.   Les questions doivent être envoyées au plus tard le 7 avril par le/la responsable de l’équipe. Ceci oblige les équipes à se former, envoyer la liste à la professeure et  avoir au moins une réunion avant le 7 avril.  
•	Il s’agit d’un travail d’équipe de six étudiants.   Un forum est ouvert avec la recommandation de fusionner deux équipes du devoir1. 
 
•	Remettre le travail composé par : le code source, les classes et le document Word (compte rendu). Mettre tout dans un dossier que vous compressez en .zip (pas rar). Le dossier doit porter le nom de l’étudiant (e) responsable des communications de l’équipe.  
 
Quelques références utiles pour le devoir  
http://java.sun.com/products/jfc/tsc/articles/painting/index. htmlhttp://java.sun.com/products/jfc/tsc/articles/painting/index.html#background http://java.sun.com/docs/books/tutorial/uiswing/index.html 
 http://fr.wikipedia.org/wiki/Algorithme_de_Dijkstra 
 
# Systeme-de-guidage-de-route-dans-un-reseau
