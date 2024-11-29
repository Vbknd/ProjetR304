package verification;

import plateau.Plateau;

public class VerifierVictoire {

    private final Plateau plateau;

    public VerifierVictoire(Plateau plateau) {
        this.plateau = plateau;
    }

    public boolean aGagne(int ligne, int colonne, char pierre) {
        return verifierLigne(ligne, colonne, pierre) ||
                verifierColonne(ligne, colonne, pierre) ||
                verifierDiagonaleMontante(ligne, colonne, pierre) ||
                verifierDiagonaleDescendante(ligne, colonne, pierre);
    }

    private boolean verifierLigne(int ligne, int colonne, char pierre) {
        int count = 1;

        // Gauche
        for (int i = colonne - 1; i >= 0 && plateau.getGrille()[ligne][i] == pierre; i--) {
            count++;
        }

        // Droite
        for (int i = colonne + 1; i < plateau.getTaille() && plateau.getGrille()[ligne][i] == pierre; i++) {
            count++;
        }

        return count >= 4;
    }

    private boolean verifierColonne(int ligne, int colonne, char pierre) {
        int count = 1;

        // Bas
        for (int i = ligne - 1; i >= 0 && plateau.getGrille()[i][colonne] == pierre; i--) {
            count++;
        }

        // Haut
        for (int i = ligne + 1; i < plateau.getTaille() && plateau.getGrille()[i][colonne] == pierre; i++) {
            count++;
        }

        return count >= 4;
    }

    private boolean verifierDiagonaleMontante(int ligne, int colonne, char pierre) {
        int count = 1;

        // Bas-gauche
        for (int i = ligne - 1, j = colonne - 1; i >= 0 && j >= 0 && plateau.getGrille()[i][j] == pierre; i--, j--) {
            count++;
        }

        // Haut-droite
        for (int i = ligne + 1, j = colonne + 1; i < plateau.getTaille() && j < plateau.getTaille() && plateau.getGrille()[i][j] == pierre; i++, j++) {
            count++;
        }

        return count >= 4;
    }

    private boolean verifierDiagonaleDescendante(int ligne, int colonne, char pierre) {
        int count = 1;

        // Haut-gauche
        for (int i = ligne + 1, j = colonne - 1; i < plateau.getTaille() && j >= 0 && plateau.getGrille()[i][j] == pierre; i++, j--) {
            count++;
        }

        // Bas-droite
        for (int i = ligne - 1, j = colonne + 1; i >= 0 && j < plateau.getTaille() && plateau.getGrille()[i][j] == pierre; i--, j++) {
            count++;
        }

        return count >= 4;
    }
}
