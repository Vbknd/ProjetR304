package joueur;

import plateau.Plateau;

public class BotMinimax implements Joueur {
    private final int profondeurMax;

    public BotMinimax(int profondeurMax) {
        if (profondeurMax <= 0) {
            throw new IllegalArgumentException("La profondeur maximale doit être supérieure à 0.");
        }
        this.profondeurMax = profondeurMax;
    }

    public int getProfondeurMax() {
        return profondeurMax;
    }

    @Override
    public String jouer(Plateau plateau, char symboleIA, char symboleAdversaire) {
        // Appelle la logique Minimax pour choisir un coup
        int[] meilleurCoup = minimax(plateau, symboleIA, symboleAdversaire, profondeurMax, true);
        if (meilleurCoup == null) {
            throw new IllegalStateException("Aucun coup légal disponible pour le BotMinimax.");
        }


        char colonne = (char) ('A' + meilleurCoup[1]);
        int ligne = meilleurCoup[0] + 1;
        return colonne + "" + ligne;
    }

    private int[] minimax(Plateau plateau, char symboleIA, char symboleAdversaire, int profondeur, boolean maximiser) {

        if (profondeur == 0 || estPlateauPlein(plateau)) {
            return new int[]{-1, -1, evaluerPlateau(plateau, symboleIA)};
        }

        int meilleurScore = maximiser ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] meilleurCoup = null;


        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {

                    plateau.placerPierre(i, j, maximiser ? symboleIA : symboleAdversaire);


                    int score = minimax(plateau, symboleIA, symboleAdversaire, profondeur - 1, !maximiser)[2];


                    plateau.getGrille()[i][j] = '.';


                    if (maximiser && score > meilleurScore || !maximiser && score < meilleurScore) {
                        meilleurScore = score;
                        meilleurCoup = new int[]{i, j, score};
                    }
                }
            }
        }


        if (meilleurCoup == null) {
            return new int[]{-1, -1, evaluerPlateau(plateau, symboleIA)};
        }
        return meilleurCoup;
    }

    private boolean estPlateauPlein(Plateau plateau) {
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.getGrille()[i][j] == '.') {
                    return false;
                }
            }
        }
        return true;
    }

    private int evaluerPlateau(Plateau plateau, char symboleIA) {

        int score = 0;
        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.getGrille()[i][j] == symboleIA) {
                    score++;
                } else if (plateau.getGrille()[i][j] != '.' && plateau.getGrille()[i][j] != symboleIA) {
                    score--;
                }
            }
        }
        return score;
    }
}
