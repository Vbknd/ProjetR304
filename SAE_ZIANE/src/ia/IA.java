package ia;

import plateau.Plateau;

public interface IA {
    /**
     * Joue un coup en fonction de la stratégie de l'IA.
     *
     * @param plateau Le plateau de jeu.
     * @param symboleIA Le symbole représentant l'IA.
     * @param symboleAdversaire Le symbole représentant l'adversaire.
     * @return La position jouée au format "colonneLigne" (ex: "D5").
     */
    String jouer(Plateau plateau, char symboleIA, char symboleAdversaire);
}
