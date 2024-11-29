package plateau;

public class Plateau {
    private char[][] grille;
    private int taille;

    public Plateau(int taille) {
        definirTaille(taille);
    }

    public void definirTaille(int taille) {
        if (taille < 5 || taille > 19) {
            throw new IllegalArgumentException("La taille doit être entre 5 et 19");
        }
        this.taille = taille;
        grille = new char[taille][taille];
        reinitialiser();
    }

    public void reinitialiser() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                grille[i][j] = '.';
            }
        }
    }

    public boolean estCoupLegal(int ligne, int colonne) {
        // Vérifie si les coordonnées sont dans les limites du plateau
        if (ligne < 0 || ligne >= taille || colonne < 0 || colonne >= taille) {
            return false;
        }
        // Vérifie si la case est vide
        return grille[ligne][colonne] == '.';
    }


    public void placerPierre(int ligne, int colonne, char symbole) {
        if (!estCoupLegal(ligne, colonne)) {
            System.err.println("Tentative de placement illégal : (" + ligne + ", " + colonne + ")");
            throw new IllegalArgumentException("Coup illégal : la case (" + ligne + ", " + colonne + ") est déjà occupée ou hors limite.");
        }

        grille[ligne][colonne] = symbole;
        System.out.println("Pierre placée sur (" + ligne + ", " + colonne + ") avec le symbole '" + symbole + "'.");
    }



    public char[][] getGrille() {
        return grille;
    }

    public int getTaille() {
        return taille;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Ligne des colonnes
        sb.append("   ");
        for (int i = 0; i < taille; i++) {
            sb.append((char) ('A' + i)).append(" ");
        }
        sb.append("\n");

        // Lignes du plateau
        for (int i = taille - 1; i >= 0; i--) {
            sb.append(i + 1).append(" ");
            if (i + 1 < 10) sb.append(" ");
            for (int j = 0; j < taille; j++) {
                sb.append(grille[i][j]).append(" ");
            }
            sb.append(i + 1).append("\n");
        }

        // Ligne des colonnes en bas
        sb.append("   ");
        for (int i = 0; i < taille; i++) {
            sb.append((char) ('A' + i)).append(" ");
        }

        return sb.toString();
    }
}
