package joueur;

import plateau.Plateau;

public interface Joueur {
    String jouer(Plateau plateau, char symboleIA, char symboleAdversaire);
}
