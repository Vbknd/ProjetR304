package joueur;

import plateau.Plateau;
import verification.VerifierVictoire;

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

        int[] meilleurCoup = minimax(plateau, symboleIA, symboleAdversaire, profondeurMax, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (meilleurCoup == null) {
            throw new IllegalStateException("Aucun coup légal disponible pour le BotMinimax.");
        }

        char colonne = (char) ('A' + meilleurCoup[1]);
        int ligne = meilleurCoup[0] + 1;

        return colonne + "" + ligne;
    }

    private int[] minimax(Plateau plateau, char symboleIA, char symboleAdversaire, int profondeur, boolean maximiser, int alpha, int beta) {
        VerifierVictoire verifierVictoire = new VerifierVictoire(plateau);

        if (profondeur == 0 || estPlateauPlein(plateau)) {
            return new int[]{-1, -1, evaluerPlateau(plateau, symboleIA, symboleAdversaire)};
        }

        int meilleurScore = maximiser ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int[] meilleurCoup = null;

        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {
                if (plateau.estCoupLegal(i, j)) {

                    plateau.placerPierre(i, j, maximiser ? symboleIA : symboleAdversaire);



                    if (verifierVictoire.aGagne(i, j, maximiser ? symboleIA : symboleAdversaire)) {
                        plateau.getGrille()[i][j] = '.';
                        return new int[]{i, j, maximiser ? 10000 : -10000};
                    }


                    int score = minimax(plateau, symboleIA, symboleAdversaire, profondeur - 1, !maximiser, alpha, beta)[2];


                    plateau.getGrille()[i][j] = '.';


                    if (maximiser) {
                        if (score > meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{i, j, score};
                        }
                        alpha = Math.max(alpha, meilleurScore);
                    } else {
                        if (score < meilleurScore) {
                            meilleurScore = score;
                            meilleurCoup = new int[]{i, j, score};
                        }
                        beta = Math.min(beta, meilleurScore);
                    }

                    // Élagage
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
        }

        return meilleurCoup == null ? new int[]{-1, -1, evaluerPlateau(plateau, symboleIA, symboleAdversaire)} : meilleurCoup;
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

    private int evaluerPlateau(Plateau plateau, char symboleIA, char symboleAdversaire) {
        int score = 0;


        int taille = plateau.getTaille();
        int centre = taille / 2;

        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (plateau.getGrille()[i][j] == symboleIA) {
                    score += 10;
                    if (Math.abs(i - centre) <= 1 && Math.abs(j - centre) <= 1) {
                        score += 5;
                    }
                } else if (plateau.getGrille()[i][j] == symboleAdversaire) {
                    score -= 10;
                    if (Math.abs(i - centre) <= 1 && Math.abs(j - centre) <= 1) {
                        score -= 5;
                    }
                }
            }
        }


        score += evaluerAlignementsPartiels(plateau, symboleIA, symboleAdversaire);

        return score;
    }


    private int evaluerAlignementsPartiels(Plateau plateau, char symboleIA, char symboleAdversaire) {
        int score = 0;


        for (int i = 0; i < plateau.getTaille(); i++) {
            for (int j = 0; j < plateau.getTaille(); j++) {

                score += calculerScoreAlignement(plateau, i, j, symboleIA);


                score -= calculerScoreAlignement(plateau, i, j, symboleAdversaire);
            }
        }

        return score;
    }

    private int calculerScoreAlignement(Plateau plateau, int ligne, int colonne, char symbole) {
        int score = 0;


        score += verifierAlignement(plateau, ligne, colonne, 0, 1, symbole);


        score += verifierAlignement(plateau, ligne, colonne, 1, 0, symbole);


        score += verifierAlignement(plateau, ligne, colonne, 1, 1, symbole);


        score += verifierAlignement(plateau, ligne, colonne, 1, -1, symbole);

        return score;
    }

    private int verifierAlignement(Plateau plateau, int ligne, int colonne, int deltaLigne, int deltaColonne, char symbole) {
        int score = 0;
        int taille = plateau.getTaille();
        int alignement = 0;
        int casesVides = 0;

        for (int k = 0; k < 4; k++) {
            int x = ligne + k * deltaLigne;
            int y = colonne + k * deltaColonne;

            if (x >= 0 && x < taille && y >= 0 && y < taille) {
                if (plateau.getGrille()[x][y] == symbole) {
                    alignement++;
                } else if (plateau.getGrille()[x][y] == '.') {
                    casesVides++;
                }
            }
        }


        if (alignement == 3 && casesVides == 1) {
            score += 100;
        } else if (alignement == 2 && casesVides == 2) {
            score += 10;
        } else if (alignement == 1 && casesVides == 3) {
            score += 1;
        }

        return score;
    }




}
