package ia;

import plateau.Plateau;
import verification.VerifierVictoire;

public class IAMoyenne implements IA {

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {
                    // Sauvegarde l'état initial de la case
                    char etatInitial = plateau.getGrille()[i][j];

                    try {
                        // Simule un coup pour l'adversaire
                        plateau.getGrille()[i][j] = symboleAdversaire;

                        // Vérifie si ce coup donne la victoire à l'adversaire
                        if (verifier.aGagne(i, j, symboleAdversaire)) {
                            // Annule la simulation et joue pour l'IA
                            plateau.getGrille()[i][j] = etatInitial;
                            plateau.placerPierre(i, j, symboleIA);
                            return convertirPosition(i, j);
                        }
                    } finally {
                        // Annule toujours la simulation
                        plateau.getGrille()[i][j] = etatInitial;
                    }
                }
            }
        }

        // Si aucun coup défensif n’est trouvé, joue comme une IA facile
        return new IAFacile().jouer(plateau, symboleIA, symboleAdversaire);
    }


    private String convertirPosition(int ligne, int colonne) {
        char colonneChar = (char) ('A' + colonne);
        return colonneChar + "" + (ligne + 1);
    }
}
