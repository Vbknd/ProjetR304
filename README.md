# Projet R3.04
Membres du groupe :
- Ali-Shan KASSOU MAMODE (203)
- Imad-Eddine BAHIJ (204)
- Ayoub BUHALLUT (204)
- Lucas REVAULT (204)
- Victor OSKANIAN (204)

## **Introduction**
Ce projet implémente un jeu de plateau interactif permettant à des joueurs humains ou à des bots de s'affronter. Les commandes textuelles permettent une interaction dynamique avec le jeu. 
Voici un résumé des fonctionnalités :

### **Ce qui fonctionne :**
- Gestion des commandes principales (`play`, `genmove`, `clear_board`, etc.).
- Détection correcte des conditions de victoire (horizontale, verticale, diagonale).
- IA utilisant l'algorithme Minimax avec profondeur configurable.
- Affichage dynamique du plateau et réinitialisation.

### **Ce qui ne fonctionne pas :**
- Les performances de l'algorithme Minimax peuvent être lentes pour les grands plateaux.
- Certaines commandes mal formées peuvent ne pas être gérées correctement.

---

## **Fonctionnalités**

### **1. Plateau**
- **Configuration dynamique** :
  - Taille configurable entre 3x3 et 19x19 via la commande `boardsize`.
- **Réinitialisation** :
  - Le plateau peut être remis à zéro avec `clear_board`.
- **Affichage interactif** :
  - L'état actuel du plateau est affiché textuellement via `showboard`.

### **2. Joueurs**
- **Humain** :
  - Joue ses coups via des commandes textuelles.
- **Bot Naïf** :
  - Joue des coups aléatoires sans stratégie.
- **Bot Minimax** :
  - Utilise l'algorithme Minimax avec évaluation stratégique et profondeur configurable.

### **3. Détection de Victoire**
- Victoires possibles horizontalement, verticalement, et diagonalement.
- La détection est gérée efficacement par la classe `VerifierVictoire`.

### **4. Commandes Disponibles**
- **`set_player <color> <type>`** : Configure un joueur (à utiliser avant toute autre commande).
  - Exemple : `set_player white human`, `set_player black minimax 3`.
- **`boardsize <taille>`** : Définit la taille du plateau.
  - Exemple : `boardsize 7`.
- **`play <color> <position>`** : Joue un coup pour le joueur spécifié.
  - Exemple : `play white A1`.
- **`genmove <color>`** : Génère automatiquement un coup pour l'IA.
- **`clear_board`** : Réinitialise le plateau.
- **`showboard`** : Affiche l'état actuel du plateau.
- **`quit`** : Termine la session de jeu.

---

## **Structure du Projet**

### **Organisation des Fichiers**
- **`Main.java`** : Point d'entrée du programme, démarre l'interpréteur.
- **`terminal`** :
  - Gère les commandes utilisateur et l'interpréteur (classe `Interpreteur`).
- **`plateau`** :
  - Gère les opérations du plateau comme les placements et réinitialisations (classe `Plateau`).
- **`commandes`** :
  - Implémente les commandes principales (`play`, `genmove`, `clear_board`).
- **`verification`** :
  - Gère la logique de détection de victoire (classe `VerifierVictoire`).
- **`joueur`** :
  - Implémente les différents types de joueurs (humain, bot naïf, bot Minimax).

---

## **Tests**

### **Couverture des Tests**
Les tests couvrent :
1. **Plateau** :
   - Initialisation, placement des coups, réinitialisation.
2. **Détection de Victoire** :
   - Cas de victoires horizontales, verticales, diagonales, et absence de victoire.
3. **Commandes** :
   - Validation des commandes textuelles, y compris les cas valides et invalides.

---

## **Améliorations Futures**
- **Optimisation de Minimax** :
  - Améliorer les performances pour les grands plateaux.
- **Interface Graphique** :
  - Ajouter une interface visuelle pour une meilleure expérience utilisateur.

