package joueur;



import plateau.Plateau;
import verification.VerifierVictoire;

import java.util.Random;

public class BotNaif implements Joueur {
    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        Random random = new Random();
        int taille = plateau.getTaille();

        for (int tentative = 0; tentative < taille * taille; tentative++) {
            int ligne = random.nextInt(taille);
            int colonne = random.nextInt(taille);
            if (plateau.estCoupLegal(ligne, colonne)) {
                char colonneChar = (char) ('A' + colonne);


                VerifierVictoire verifierVictoire = new VerifierVictoire(plateau);
                if (verifierVictoire.aGagne(ligne, colonne, symboleIA)) {
                    return symboleIA + " wins!";
                }

                return colonneChar + "" + (ligne + 1);

            }

        }

        throw new IllegalStateException("Aucun coup légal disponible.");
    }
}
