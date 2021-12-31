## Compilation

Pour compiler le projet, se placer dans le dossier racine du projet et lancer la  
commande  

  ./gradlew build  

## LANCEMENT DU PROGRAMME

Pour lancer la version "graphique" il faut passer l'un des deux arguments suivants  
(et seulement l'un des deux, les autres arguments ne seront pas pris en compte)  

  ``./gradlew run --args="-gui"``  
ou  
  ``./gradlew run --args="-graphics"``  

Pour lancer la version "console" il faut passer les arguments pour générer un  
fractale.  

Pour générer un ensemble de Julia avec les paramètres par défaut :  
  ``./gradlew run --args="-julia"``  

Pour générer le fractal Mandelbrot avec les paramètres par défaut :  
  ``./gradlew run --args="-mandelbrot"``  

Pour lancer le programme dans tout les cas, il faut utiliser cette commande :  
  ``./gradlew run --args="[les arguments ici]"``  
et mettre entre les guillemets les options que vous trouverez avec la commande  
  ``./gradlew run --args="-help"``  
afin d'avoir la documentation.  

## PRINCIPAUX CHOIX TECHNIQUES

Pour la version "console" nous avons pris la bibliothèque d'Apache CLI afin de  
mieux gérer les arguments de la ligne de commande. Pour l'interface graphique,  
nous l'avons construite en utilisant l'injection de dépendance de JavaFX pour  
mieux manipuler et avoir facilement accès aux éléments de l'IG.  
La structure du projet est une interface Fractal que deux classes implémentent :  
Julia et Mandelbrot, une classe Complex afin d'améliorer la génération des  
fractales et des classes utilitaires permettant de générer une image PNG et un  
fichier TXT décrivant le fractal et permettant sa génération ultérieurement.  

## COMPARAISON AVEC LA PROGRAMMATION PARALLÈLE

En utilisant un ForkJoinPool et en divisant l'image en plusieurs parties pour que  
les threads travaillent en parallèle, les temps de calcul de l'image ont été  
nettement améliorés :  

Julia (default parameters) : 60s env. -> 20s env.  
Mandelbrot (default parameters) : 20s env. -> 10s env.  
