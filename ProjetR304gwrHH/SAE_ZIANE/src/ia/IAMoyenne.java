package ia;

import plateau.Plateau;
import verification.VerifierVictoire;

public class IAMoyenne implements IA {

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        // Parcourt les cases pour bloquer un coup gagnant de l'adversaire
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {
                    // Simule un coup adverse
                    plateau.placerPierre(i, j, symboleAdversaire);
                    if (verifier.aGagne(i, j, symboleAdversaire)) {
                        plateau.placerPierre(i, j, '.'); // Annule la simulation
                        plateau.placerPierre(i, j, symboleIA); // Bloque l'adversaire
                        return convertirPosition(i, j);
                    }
                    plateau.placerPierre(i, j, '.'); // Annule la simulation
                }
            }
        }

        // Sinon, joue comme une IA facile
        return new IAFacile().jouer(plateau, symboleIA, symboleAdversaire);
    }

    private String convertirPosition(int ligne, int colonne) {
        char colonneChar = (char) ('A' + colonne);
        return colonneChar + "" + (ligne + 1);
    }
}
