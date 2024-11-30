package ia;

import plateau.Plateau;
import verification.VerifierVictoire;

public class IADifficile implements IA {

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        VerifierVictoire verifier = new VerifierVictoire(plateau);

        // Tente de gagner
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {
                    // Sauvegarde l'état initial de la case
                    char etatInitial = plateau.getGrille()[i][j];

                    try {
                        // Simule un coup pour l'IA
                        plateau.getGrille()[i][j] = symboleIA;
                        if (verifier.aGagne(i, j, symboleIA)) {
                            return convertirPosition(i, j);
                        }
                    } finally {
                        // Restaure l'état initial de la case
                        plateau.getGrille()[i][j] = etatInitial;
                    }
                }
            }
        }

        // Tente de bloquer l'adversaire
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {
                    // Sauvegarde l'état initial de la case
                    char etatInitial = plateau.getGrille()[i][j];

                    try {
                        // Simule un coup pour l'adversaire
                        plateau.getGrille()[i][j] = symboleAdversaire;
                        if (verifier.aGagne(i, j, symboleAdversaire)) {
                            // Bloque le coup gagnant de l'adversaire
                            plateau.getGrille()[i][j] = symboleIA;
                            return convertirPosition(i, j);
                        }
                    } finally {
                        // Restaure l'état initial de la case
                        plateau.getGrille()[i][j] = etatInitial;
                    }
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
