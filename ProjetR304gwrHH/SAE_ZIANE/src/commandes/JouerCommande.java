package commandes;

import plateau.Plateau;
import verification.VerifierVictoire;

public class JouerCommande implements Commande {
    private final Plateau plateau;
    private final String couleur;
    private final String position;

    public JouerCommande(Plateau plateau, String couleur, String position) {
        this.plateau = plateau;
        this.couleur = couleur;
        this.position = position;
    }

    @Override
    public String executer() {
        try {
            int[] coords = parsePosition(position);
            char pierre = couleur.equals("black") ? 'X' : 'O';
            plateau.placerPierre(coords[0], coords[1], pierre);

            // Vérification de la victoire
            VerifierVictoire verifier = new VerifierVictoire(plateau);
            if (verifier.aGagne(coords[0], coords[1], pierre)) {
                return "=" + couleur + "wins!";
            }

            return position; // Coup réussi
        } catch (IllegalArgumentException e) {
            return "? " + e.getMessage();
        }
    }

    private int[] parsePosition(String position) {
        char colonne = position.charAt(0);
        int ligne = Integer.parseInt(position.substring(1)) - 1;
        return new int[]{ligne, colonne - 'A'};
    }
}
