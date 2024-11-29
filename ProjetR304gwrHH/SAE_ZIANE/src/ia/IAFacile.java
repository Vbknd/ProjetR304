package ia;

import plateau.Plateau;
import java.util.Random;

public class IAFacile implements IA {

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        Random random = new Random();
        int taille = plateau.getTaille();

        // Parcourt les cases pour trouver un coup légal
        for (int tentative = 0; tentative < taille * taille; tentative++) {
            int ligne = random.nextInt(taille);
            int colonne = random.nextInt(taille);
            if (plateau.estCoupLegal(ligne, colonne)) {
                plateau.placerPierre(ligne, colonne, symboleIA);
                return convertirPosition(ligne, colonne);
            }
        }

        throw new IllegalStateException("Aucun coup légal disponible pour l'IA.");
    }

    private String convertirPosition(int ligne, int colonne) {
        char colonneChar = (char) ('A' + colonne);
        return colonneChar + "" + (ligne + 1);
    }
}
